package com.therivalfaction.gtfoodclub;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 24-Feb-17.
 */

public class DataHelper {

    private Context context;
    private static final String APP_PREFERENCES = "campus_food_club_preferences";
    private static final String KEYWORDS_SET_KEY = "keywords_set";
    private static final String IS_NOTIFICATION_ON_KEY = "isNotificationOn";

    public DataHelper(Context _context)
    {
        context = _context;
    }

    public ArrayList<String> loadKeywords()
    {
        SharedPreferences sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        //get keywords
        Set<String> keywordSet = sharedPref.getStringSet(KEYWORDS_SET_KEY, null);
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

    public void addKeyword(String keyword)
    {
        ArrayList<String> keywords = loadKeywords();
        keywords.add(keyword);
        saveKeywords(keywords);
    }

    public void removeKeyword(String keyword)
    {
        ArrayList<String> keywords = loadKeywords();
        keywords.remove(keyword);
        saveKeywords(keywords);
    }

    private void saveKeywords(ArrayList<String> keywords)
    {
        Set<String> keywordsSet = new HashSet<String>();
        int i = 1;
        for (String s : keywords)
        {
            Log.d("AC1","Adding >>"+s+"<<");
            keywordsSet.add(String.format("%03d$%s",i,s));
            i++;
        }
        SharedPreferences sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(KEYWORDS_SET_KEY,keywordsSet);
        editor.commit();
    }

    public boolean isNotificationOn()
    {
        SharedPreferences sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IS_NOTIFICATION_ON_KEY, true);
    }

    public void setIsNotificationOn(boolean b)
    {
        SharedPreferences sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_NOTIFICATION_ON_KEY, b);
        editor.commit();
    }
}
