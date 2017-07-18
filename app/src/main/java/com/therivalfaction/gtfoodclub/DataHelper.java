package com.therivalfaction.gtfoodclub;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 24-Feb-17.
 */

public class DataHelper {

    private Context context;

    public DataHelper(Context _context)
    {
        context = _context;
    }

    public ArrayList<String> loadKeywords()
    {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.keyword_sharedPref_file_key),
                Context.MODE_PRIVATE);
        Set<String> keywordSet = sharedPref.getStringSet(context.getString(R.string.keyword_sharedPref_set_key),
                null);
        if(keywordSet==null) // this is being opened the first time. generate default set
        {
            keywordSet = new HashSet<String>();
            keywordSet.add("001$Pizza");
            keywordSet.add("002$Free Food");
            keywordSet.add("004$Lunch");
            keywordSet.add("003$Food");
            keywordSet.add("004$Refreshment");
            keywordSet.add("005$Coffee");
            keywordSet.add("006$Free");
        }
        ArrayList<String> sortedKeywords = new ArrayList<>(keywordSet);
        Collections.sort(sortedKeywords);
        for(int i = 0;i<sortedKeywords.size();i++)
            sortedKeywords.set(i, sortedKeywords.get(i).substring(4)); //remove the sorting index
        return sortedKeywords;

    }
}
