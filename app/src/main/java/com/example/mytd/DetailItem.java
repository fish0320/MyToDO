package com.example.mytd;

public class DetailItem {
    private int id;
    private String Name;
    private String Content;
    private String Date;
    public DetailItem() {
        super();
        Name = "";
        Content = "";
        Date = "";
    }
    public DetailItem(String Name, String Content,String Date) {
        super();
        this.Name = Name;
        this.Content = Content;
        this.Date = Date;
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
    public String getContent() {
        return Content;
    }
    public void setContent(String Content) {
        this.Content = Content;
    }
    public String getDate() {
        return Date;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }

}
