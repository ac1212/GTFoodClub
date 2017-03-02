package com.therivalfaction.gtfoodclub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.titleTextView);
        TextView tvWord = (TextView) convertView.findViewById(R.id.wordTextView);
        tvTitle.setText(gtEvent.title);
        MainActivity ma = (MainActivity) getContext();
        ArrayList<String> keywords = ma.mKeywordArrayAdapter.getItems();
        tvWord.setText(gtEvent.getWord(keywords));
        return convertView;
    }
}
