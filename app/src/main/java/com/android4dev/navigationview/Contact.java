package com.android4dev.navigationview;

/**
 * Created by priya on 7/16/2015.
 */
public class Contact {

    String recipient , textBody,name;

    public Contact(String recipient , String textBody, String name)
    {
        this.recipient = recipient;
        this.textBody = textBody ;
        this.name = name;

    }
    public Contact(String name )
    {
        this.name = name;

    }



}
