package com.therivalfaction.gtfoodclub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 22-Feb-17.
 */

public class KeywordArrayAdapter extends ArrayAdapter<Object> {

    DataHelper mDH;
    private static final int TYPE_KEYWORD = 0;
    private static final int TYPE_ADD = 1;



    public KeywordArrayAdapter(Context context, int resource, DataHelper _dh) {
        super(context, resource);
        mDH = _dh;
        addAll(mDH.loadKeywords());
        add(1);
    }

    public ArrayList<String> getItems()
    {
        ArrayList<String> items = new ArrayList<String>();
        for(int i = 0;i<getCount()-1;i++) items.add((String)getItem(i));
        return items;
    }

    @Override
    public int getItemViewType(int position) {
        return (getItem(position) instanceof String) ? TYPE_KEYWORD : TYPE_ADD;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int viewType = getItemViewType(position);
        if(viewType==0)
        {
            Log.d("AC1","Item is keyword: "+ getItem(position));
        }
        else
        {
            Log.d("AC1","Item is add button: "+ getItem(position));
        }
        switch (viewType)
        {
            case TYPE_KEYWORD:
                String keyword = (String) getItem(position);
                convertView = inflater.inflate(R.layout.keyword_list_item,parent,false);
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
                break;
            case TYPE_ADD:
                convertView = inflater.inflate(R.layout.add_keyword_list_item,parent,false);
                LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.add_keyword_linearlayout);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final EditText etNewTask = new EditText(getContext());
                        AlertDialog dialog = new AlertDialog.Builder(getContext())
                                .setTitle("Add new tag")
                                .setView(etNewTask)
                                .setPositiveButton("Add", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int pos)
                                    {
                                        String keyword = etNewTask.getText().toString();
                                        addKeyword(keyword);
                                    }})
                                .setNegativeButton("Cancel",null)
                                .create();
                        dialog.show();
                    }
                });
                break;
            default:
                break;
        }
        return convertView;

    }

    private void deleteKeyword(int position)
    {
        if(getCount()>2)
        {
            mDH.removeKeyword((String) getItem(position));
            clear();
            addAll(mDH.loadKeywords());
            add(1);
            notifyDataSetChanged();
        }
    }

    private void addKeyword(String keyword)
    {
        mDH.addKeyword(keyword);
        clear();
        addAll(mDH.loadKeywords());
        add(1);
        notifyDataSetChanged();
    }

}
