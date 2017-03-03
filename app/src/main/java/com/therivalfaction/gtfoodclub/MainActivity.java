package com.therivalfaction.gtfoodclub;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    GTEventArrayAdapter adapter;
    ActionBarDrawerToggle mDrawerToggle;
    KeywordArrayAdapter mKeywordArrayAdapter;

    //async XML downloader class
    private class XMLDownloader extends AsyncTask<String, Void, ArrayList<GTEvent>> {

        @Override
        protected ArrayList<GTEvent> doInBackground(String... strings) {
            ArrayList<GTEvent> ans = new ArrayList<GTEvent>();
            try {
                ans = getGoodEventsFromNet(strings[0]);
            } catch (Exception ex) {
                Log.e("AC", ex.toString());
            }
            return ans;
        }

        // once retrieved, display the good events
        @Override
        protected void onPostExecute(ArrayList<GTEvent> gtEvents) {
            ListView lv = (ListView) findViewById(R.id.mainListView);
            adapter.addAll(gtEvents);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    GTEvent gtEvent = adapter.getItem(i);
                    Uri uri = Uri.parse(gtEvent.link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }

        private ArrayList<GTEvent> getGoodEventsFromNet(String urlString) throws XmlPullParserException, IOException, ParseException {
            InputStream stream = null;

            //connect to url
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            stream = conn.getInputStream();

            //receive and parse data to XML, then to GTEvent
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            ArrayList<GTEvent> gtEventList = new ArrayList<GTEvent>();
            int count = 0;
            while (parser.next() != XmlPullParser.END_DOCUMENT && count < 10) {
                //unles it is an item, we are not interested
                if (parser.getEventType() != XmlPullParser.START_TAG) continue;
                if (!parser.getName().equals("item")) continue;
                //found an item, read tags and save those that matter
                GTEvent gtEvent = new GTEvent();
                do {
                    if (parser.getEventType() == XmlPullParser.START_TAG) {
                        String tagname = parser.getName();
                        parser.next();
                        switch (tagname) {
                            case "title":
                                gtEvent.title = parser.getText();
                                break;
                            case "link":
                                String tagText = parser.getText();
                                gtEvent.link = tagText;
                                gtEvent.id = tagText.substring(tagText.lastIndexOf('/') + 1);
                                break;
                            case "description":
                                gtEvent.description = parser.getText();
                                //TODO: grabbing the first date. grab all dates on recurring events.
                                int idx = gtEvent.description.indexOf("dc:date");
                                String dateString = gtEvent.description.substring(idx+42,idx+67);
                                dateString = dateString.substring(0,22)+dateString.substring(23,25);
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                                Date time = df.parse(dateString);
                                gtEvent.time = time;
                                break;
                            default:
                        }
                    }
                    parser.nextTag();
                }
                while (!(parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equals("item")));
                //if the event has the desired keywords, add the event to the list
                if (gtEvent.hasDesiredText(mKeywordArrayAdapter.getItems()) && !gtEventList.contains(gtEvent))
                    gtEventList.add(gtEvent);
            }
            return gtEventList;
        }
    }


    private static final String URLStringCalendar = "http://www.calendar.gatech.edu/feeds/events.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_closed){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // load keywords
        mKeywordArrayAdapter = new KeywordArrayAdapter(this, R.layout.keyword_list_item);
        DataHelper dh = new DataHelper(this);
        mKeywordArrayAdapter.addAll(dh.loadKeywords());
        ListView keywordListView = (ListView) findViewById(R.id.drawerListView);
        keywordListView.setAdapter(mKeywordArrayAdapter);

        adapter = new GTEventArrayAdapter(this, R.layout.list_item);
        new XMLDownloader().execute(URLStringCalendar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mDrawerToggle.onOptionsItemSelected(item)) return true;
        switch (item.getItemId()) {
            case R.id.about_menu_item:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
