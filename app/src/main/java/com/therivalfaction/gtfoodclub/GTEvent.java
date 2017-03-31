package com.therivalfaction.gtfoodclub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Created by User on 18-Feb-17.
 */
public class GTEvent {
    public String id;
    public String title;
    public String link;
    public Date time;
    public String description;

    public boolean hasDesiredText()
    {
        String s = description.toLowerCase();
        return s.contains("pizza")||s.contains("free")||s.contains("refreshment")||s.contains("coffee")||s.contains("snack");
    }

    public boolean hasDesiredText(Collection<String> keywords)
    {
        String s = description.toLowerCase();
        for (String kw : keywords)
            if(s.contains(kw.toLowerCase()))
                return true;
        return false;
    }

    public String getWord(ArrayList<String> keywords)
    {
        String s = description.toUpperCase();
        String[] sortedKeywords = keywords.toArray(new String[keywords.size()]);
        for (String kw : sortedKeywords)
            if(s.contains(kw.toUpperCase()))
                return kw.toUpperCase();
        return null;
    }

    public String getSentence(String word)
    {
        String s = description.toUpperCase();
        int idx = s.indexOf(word); // get to the beginning of the word
        String sentence = description.substring(idx, idx+100); // get next 100 chars
        if(sentence.contains("<")) sentence = sentence.substring(0,sentence.indexOf("<")); // cut off if XML tag reached
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

    public String getWord()
    {
        String s = description.toUpperCase();
        if(s.contains("PIZZA")) return "PIZZA";
        if(s.contains("FREE")) return "FREE";
        if(s.contains("REFRESHMENT")) return "REFRESHMENT";
        if(s.contains("COFFEE")) return "COFFEE";
        if(s.contains("SNACK")) return "SNACK";
        return "OTHER";
    }
}
