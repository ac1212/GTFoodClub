package com.therivalfaction.gtfoodclub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by User on 22-Feb-17.
 */

public class KeywordArrayAdapter extends ArrayAdapter<String> {
    public KeywordArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String keyword = getItem(position);
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.keyword_list_item,parent,false);
        TextView tv = (TextView) convertView.findViewById(R.id.keywordTextView);
        tv.setText(keyword);
        return convertView;
    }
}
