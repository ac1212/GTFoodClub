package com.therivalfaction.gtfoodclub;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<GTEvent> adapter;

    //async XML downloader class
    private class XMLDownloader extends AsyncTask<String, Void, ArrayList<GTEvent>>
    {

        @Override
        protected ArrayList<GTEvent> doInBackground(String... strings) {
            ArrayList<GTEvent> ans = new ArrayList<GTEvent>();
            try
            {
                ans = getGoodEventsFromNet(strings[0]);
            }
            catch (Exception ex)
            {
                Log.e("AC",ex.toString());
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

        private ArrayList<GTEvent> getGoodEventsFromNet(String urlString) throws XmlPullParserException, IOException
        {
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
            //parser.require(XmlPullParser.START_TAG,null,"channel");
            int count = 0;
            while(parser.next() != XmlPullParser.END_DOCUMENT && count <10)
            {
                //unles it is an item, we are not interested
                if(parser.getEventType() != XmlPullParser.START_TAG) continue;
                if(!parser.getName().equals("item")) continue;
                //found an item, read tags and save those that matter
                GTEvent gtEvent = new GTEvent();
                do
                {
                    if(parser.getEventType() == XmlPullParser.START_TAG)
                    {
                        String tagname = parser.getName();
                        parser.next();
                        switch (tagname)
                        {
                            case "title":
                                gtEvent.title = parser.getText();
                                break;
                            case "link":
                                gtEvent.link = parser.getText();
                                break;
                            case "description":
                                gtEvent.description = parser.getText();
                                break;
                            default:
                        }
                    }
                    parser.nextTag();
                }while(!(parser.getEventType()==XmlPullParser.END_TAG && parser.getName().equals("item")));
                //if the event has the desired keywords, add the event to the list
                if(gtEvent.hasDesiredText())
                    gtEventList.add(gtEvent);
            }
            return gtEventList;
        }
    }


    private static final String URLString = "http://www.calendar.gatech.edu/feeds/events.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<GTEvent>(this,R.layout.list_item);
        new XMLDownloader().execute(URLString);
    }
}
