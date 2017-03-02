package com.therivalfaction.gtfoodclub;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
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

    public Set<String> loadKeywords()
    {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.keyword_sharedPref_file_key),
                Context.MODE_PRIVATE);
        Set<String> keywordSet = sharedPref.getStringSet(context.getString(R.string.keyword_sharedPref_set_key),
                null);
        if(keywordSet==null) // this is being opened the first time. generate default set
        {
            keywordSet = new HashSet<String>();
            keywordSet.add("Pizza");
            keywordSet.add("Free Food");
            keywordSet.add("Refreshment");
            keywordSet.add("Coffee");
            keywordSet.add("Free");
        }
        return keywordSet;

    }
}
