package com.example.mytd;

public class DoneItem {
    private String Time;
    private String Content;


    public DoneItem() {
        super();
        Time = "";
        Content = "";

    }
    public DoneItem(String Time,String Content) {
        super();
        this.Time = Time;
        this.Content = Content;
    }
    public void setTime(String Time) {
        this.Time = Time;
    }
    public String getTime() {
        return Time;
    }
    public void setContent(String Content) {
        this.Content = Content;
    }
    public String getContent() {
        return Content;
    }
}
