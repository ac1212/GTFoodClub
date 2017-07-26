package com.therivalfaction.gtfoodclub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 22-Feb-17.
 */

public class KeywordArrayAdapter extends ArrayAdapter<String> {

    DataHelper mDH;


    public KeywordArrayAdapter(Context context, int resource, DataHelper _dh) {
        super(context, resource);
        mDH = _dh;
        addAll(mDH.loadKeywords());
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

        //set up delete button
        Button b = (Button) convertView.findViewById(R.id.keyword_delete_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKeyword(position);
            }
        });
        return convertView;
    }

    private void deleteKeyword(int position)
    {
        if(getCount()==1)
        {

        }
        remove(getItem(position));
        mDH.saveKeywords(getItems());
    }
}
