package com.therivalfaction.gtfoodclub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 22-Feb-17.
 */

public class KeywordArrayAdapter extends ArrayAdapter<String> {



    public KeywordArrayAdapter(Context context, int resource) {
        super(context, resource);

    }

    public ArrayList<String> getItems()
    {
        ArrayList<String> items = new ArrayList<String>();
        for(int i = 0;i<getCount();i++)
            items.add(getItem(i));
        return items;
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
