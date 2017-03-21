package com.example.lucas.tasks;

/**
 * Created by lkm02 on 3/21/2017.
 */

public class TodoItem {
    private String text;
    private String date;
    private Boolean checked;

    public TodoItem(String text, String date) {
        this.text = text;
        this.date = date;
        this.checked = false;
    }

    public TodoItem(String text) {
        this(text,"");
    }

    //return text in the textview
    public String getText() {
        return text;
    }

    //set the text within the textview
    public void setText(String text){
        this.text = text;
    }

    //set whether the listitem is checked
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getChecked() {
        return checked;
    }

    //
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
}
