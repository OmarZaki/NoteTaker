package com.example.omar.notetaker.DataModel;

import java.util.Date;

/**
 * Created by OMAR on 10/30/2016.
 */

public class Note {
    public static final String NOTE_TABLE="note";
    public static final String NOTE_ID="nid";
    public static final String NOTE_TITLE="ntitle";
    public static final String NOTE_TEXT="ntitle";
    public static final String NOTE_DATE="ndate";

    // constructors
    public Note (){
        super();
    }
    public Note(String title, String text) {
        this.title = title;
        this.text = text;
      //  this.date = date;
    }


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    long Id;
    String title;
    String text;
    Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
