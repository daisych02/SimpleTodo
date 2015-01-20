package com.daisych.simpletodo;

/**
 * Created by daisych on 1/19/15.
 */
public class Item {
    private String name;
    private String date;

    public Item (String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Item (String str) {
        String[] attrs = str.split(" ");
        if(attrs.length >= 2) {
            this.name = attrs[0];
            this.date = attrs[1];
        }
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean Equals (Item another) {
        return this.name == another.getName();
    }

}
