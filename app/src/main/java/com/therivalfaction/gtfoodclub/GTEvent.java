package com.therivalfaction.gtfoodclub;

import java.util.Collection;

/**
 * Created by User on 18-Feb-17.
 */
public class GTEvent {
    public String id;
    public String title;
    public String link;
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

    public String getWord(Collection<String> keywords)
    {
        String s = description.toUpperCase();
        for (String kw : keywords)
            if(s.contains(kw.toUpperCase()))
                return kw.toUpperCase();
        return null;
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
