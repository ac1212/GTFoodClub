package com.therivalfaction.gtfoodclub;

/**
 * Created by User on 18-Feb-17.
 */
public class GTEvent {
    public String title;
    public String link;
    public String description;

    public boolean hasDesiredText()
    {
        String s = description.toLowerCase();
        return s.contains("pizza")||s.contains("free")||s.contains("refreshment")||s.contains("coffee")||s.contains("snack ");
    }

    @Override
    public String toString() {
        return title;
    }
}
