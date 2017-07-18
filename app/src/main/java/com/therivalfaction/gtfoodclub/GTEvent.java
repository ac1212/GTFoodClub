package com.therivalfaction.gtfoodclub;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Created by User on 18-Feb-17.
 */
public class GTEvent {
    public final String id;
    public final String title;
    public final String link;
    public final Date time;
    public final String word;
    public final String description;

    public GTEvent(String id, String title, String link, Date time, String word, String description) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.time = time;
        this.word = word;
        this.description = description;
    }

    public String getSentence()
    {
        String s = title.toUpperCase() + " < " + description.toUpperCase();
        int idx = s.indexOf(word); // get to the beginning of the word
        s = title + " < " + description;
        String sentence = s.substring(idx, s.length()<= idx+100 ? s.length() : idx+100); // get next 100 chars
        if(sentence.contains("<")) sentence = sentence.substring(0,sentence.indexOf("<")); // cut off if XML tag reached
        //if(sentence.toUpperCase().compareTo(word)==0) Log.d("AC1",s);
        return "..."+sentence+"...";
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        GTEvent that = (GTEvent) obj;
        return this.id.equals(that.id);
    }
}
