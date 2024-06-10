package com.example.mytd;

public class ListItem {
    private String Name;
    private String Start;
    private String Times;

    public ListItem() {
        super();
        Name = "";
        Start = "";
        Times = "";
    }
    public ListItem(String Name,String Start,String Times) {
        super();
        this.Name = Name;
        this.Start = Start;
        this.Times = Times;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getName() {
        return Name;
    }
    public void setStart(String Start) {
        this.Start = Start;
    }
    public String getStart() {
        return Start;
    }
    public void setTimes(String Times) {
        this.Times = Times;
    }
    public String getTimes() {
        return Times;
    }
}
