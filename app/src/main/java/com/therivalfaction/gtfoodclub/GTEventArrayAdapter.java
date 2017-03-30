package com.therivalfaction.gtfoodclub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 20-Feb-17.
 */

public class GTEventArrayAdapter extends ArrayAdapter<GTEvent> {
    public GTEventArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GTEvent gtEvent = getItem(position);
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_list_item,parent,false);
        //set title
        TextView tvTitle = (TextView) convertView.findViewById(R.id.titleTextView);
        tvTitle.setText(gtEvent.title);
        //set keyword
        TextView tvWord = (TextView) convertView.findViewById(R.id.wordTextView);
        MainActivity ma = (MainActivity) getContext();
        ArrayList<String> keywords = ma.mKeywordArrayAdapter.getItems();
        tvWord.setText(gtEvent.getWord(keywords));
        //set date
        TextView tvDate = (TextView) convertView.findViewById(R.id.dateTextView);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int curY = cal.get(Calendar.YEAR);
        int curM = cal.get(Calendar.MONTH);
        int curD = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(gtEvent.time);
        int evtY = cal.get(Calendar.YEAR);
        int evtM = cal.get(Calendar.MONTH);
        int evtD = cal.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdfLater = new SimpleDateFormat("MMM dd");
        SimpleDateFormat sdfToday = new SimpleDateFormat("hh:mm aa");
        if(evtY==curY && evtM==curM && evtD==curD)
            tvDate.setText(sdfToday.format(cal.getTime()));
        else
            tvDate.setText(sdfLater.format(cal.getTime()));
        return convertView;
    }
}
