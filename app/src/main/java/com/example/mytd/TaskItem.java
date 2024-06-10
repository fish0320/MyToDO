package com.example.mytd;

public class TaskItem {
    private int id;
    private String Name;
    private String Detail;
    private String Start;
    private int Times;
    private String State;

    public TaskItem() {
        super();
        Name = "";
        Detail = "";
        Start = "";
        Times = 0;
        State = "";

    }
    public TaskItem(String Name, String Detail,String Start,Integer Times,String State) {
        super();
        this.Name = Name;
        this.Detail = Detail;
        this.Start = Start;
        this.Times = Times;
        this.State = State;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getDetail() {
        return Detail;
    }
    public void setDetail(String Detail) {
        this.Detail = Detail;
    }
    public String getStart() {
        return Start;
    }
    public void setStart(String Start) {
        this.Start = Start;
    }
    public int getTimes() {
        return Times;
    }
    public void setTimes(int Times) {
        this.Times = Times;
    }
    public String getState() {
        return State;
    }
    public void setState(String State) {
        this.State = State;
    }


}
