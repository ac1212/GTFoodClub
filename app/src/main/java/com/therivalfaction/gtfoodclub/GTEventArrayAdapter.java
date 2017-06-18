package com.therivalfaction.gtfoodclub;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        String keyword = gtEvent.getWord(keywords);
        tvWord.setText(keyword);
        //set date
        TextView tvDate = (TextView) convertView.findViewById(R.id.dateTextView);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int curY = cal.get(Calendar.YEAR);
        int curM = cal.get(Calendar.MONTH);
        int curD = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(gtEvent.time);
        int evtY = cal.get(Calendar.YEAR);
        int evtM = cal.get(Calendar.MONTH);
        int evtD = cal.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdfLater = new SimpleDateFormat("MMM dd");
        SimpleDateFormat sdfToday = new SimpleDateFormat("hh:mm aa");
        if(evtY==curY && evtM==curM && evtD==curD) { // event is today
            tvDate.setText(sdfToday.format(cal.getTime()));
            tvDate.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        }
        else {
            tvDate.setText(sdfLater.format(cal.getTime()));
            tvDate.setTextColor(tvTitle.getTextColors());
        }
        // set description
        TextView tvDesc = (TextView) convertView.findViewById(R.id.descTextView);
        tvDesc.setText(gtEvent.getSentence(keyword));
        return convertView;
    }
}
