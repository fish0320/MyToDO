package com.example.mytd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DBManager {
    private static final String TAG = "DBManager";
    private ArrayList<HashMap<String, String>> listItems;
    private ArrayList<ListItem> Items;

    private ArrayList<DoneItem> DoneItems;

    private DBHelper dbHelper;
    private String TBNAME;
    private String TBNAME_2;
    Date startDate;
    Date endDate;
    Date currentDate;
    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
        TBNAME_2 = DBHelper.TB_NAME_2;
    }
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    public void add(TaskItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("detail", item.getDetail());
        values.put("start", item.getStart());
        values.put("times", item.getTimes());
        values.put("state", item.getState());
        Log.i(TAG,"插入："+item.getName());
        db.insert(TBNAME, null, values);
        db.close();
    }
    public void addDetail(DetailItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("content", item.getContent());
        values.put("date", item.getDate());
        Log.i(TAG,"插入："+item.getName());
        db.insert(TBNAME_2, null, values);
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteDetail(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME_2, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void update(TaskItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("detail", item.getDetail());
        values.put("start", item.getStart());
        values.put("times", item.getTimes());
        values.put("state", item.getState());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }
    @SuppressLint("Range")
    public List<TaskItem> listAll(){
        List<TaskItem> taskList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            taskList = new ArrayList<TaskItem>();
            while(cursor.moveToNext()){
                TaskItem item = new TaskItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setStart(cursor.getString(cursor.getColumnIndex("START")));
                item.setDetail(cursor.getString(cursor.getColumnIndex("DETAIL")));
                item.setTimes(cursor.getInt(cursor.getColumnIndex("TIMES")));
                item.setState(cursor.getString(cursor.getColumnIndex("STATE")));

                taskList.add(item);
            }
            cursor.close();
        }
        db.close();
        return taskList;
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String,String>> listTask(){
        listItems = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("itemName", cursor.getString(cursor.getColumnIndex("NAME")));
                map.put("itemStart", "From " + cursor.getString(cursor.getColumnIndex("START")));
                map.put("itemTimes", "已完成" + cursor.getInt(cursor.getColumnIndex("TIMES")) + "天");
                listItems.add(map);
                Log.i(TAG,"列表项："+ cursor.getString(cursor.getColumnIndex("NAME")));
            }
            cursor.close();
        }
        db.close();
        return listItems;
    }

    @SuppressLint("Range")
    public ArrayList<ListItem> listAllItem(){
        Items = new ArrayList<ListItem>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                ListItem item = new ListItem();
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setStart( "From " + cursor.getString(cursor.getColumnIndex("START")));
                item.setTimes("已完成" + cursor.getInt(cursor.getColumnIndex("TIMES")) + "天");
                Items.add(item);
                Log.i(TAG,"列表项："+ cursor.getString(cursor.getColumnIndex("NAME")));
            }
            cursor.close();
        }
        db.close();
        return Items;
    }

    @SuppressLint("Range")
    public ArrayList<DoneItem> listDoneItem(String name){
        DoneItems = new ArrayList<DoneItem>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME_2, null, "NAME=?",new String[]{String.valueOf(name)}, null, null, null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                DoneItem item = new DoneItem();
                item.setTime(cursor.getString(cursor.getColumnIndex("DATE")));
                item.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));
                DoneItems.add(item);
                Log.i(TAG,"列表项："+ cursor.getString(cursor.getColumnIndex("DATE")));
            }
            cursor.close();
        }
        db.close();
        return DoneItems;
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> listAllTodo(){
        ArrayList<HashMap<String, String>> Items = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                String currentTime = getCurrentDate();
                String startTime = cursor.getString(cursor.getColumnIndex("START"));
                String endTime = cursor.getString(cursor.getColumnIndex("STATE"));
                DateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
                try {
                    startDate = (Date)formatter.parse(startTime);
                    endDate = (Date)formatter.parse(endTime);
                    currentDate = (Date)formatter.parse(currentTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if(startDate.getTime()<=currentDate.getTime()&&endDate.getTime()>=currentDate.getTime()){
                    HashMap<String,String> item = new HashMap<String,String>();
                    item.put("itemName", cursor.getString(cursor.getColumnIndex("NAME")));
                    Items.add(item);
                    Log.i(TAG,"列表项："+ cursor.getString(cursor.getColumnIndex("NAME")));
                }


            }
            cursor.close();
        }
        db.close();
        return Items;
    }
    @SuppressLint("Range")
    public TaskItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        TaskItem taskItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            taskItem = new TaskItem();
            taskItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            taskItem.setName(cursor.getString(cursor.getColumnIndex("Name")));
            taskItem.setStart(cursor.getString(cursor.getColumnIndex("Start")));
            taskItem.setDetail(cursor.getString(cursor.getColumnIndex("Detail")));
            taskItem.setTimes(cursor.getInt(cursor.getColumnIndex("Times")));
            taskItem.setState(cursor.getString(cursor.getColumnIndex("State")));;
            cursor.close();
        }
        db.close();
        return taskItem;
    }

    @SuppressLint("Range")
    public TaskItem findByNAME(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "NAME=?", new String[]{String.valueOf(name)}, null, null, null);
        TaskItem taskItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            taskItem = new TaskItem();
            taskItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            taskItem.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            taskItem.setStart(cursor.getString(cursor.getColumnIndex("START")));
            taskItem.setDetail(cursor.getString(cursor.getColumnIndex("DETAIL")));
            taskItem.setTimes(cursor.getInt(cursor.getColumnIndex("TIMES")));
            taskItem.setState(cursor.getString(cursor.getColumnIndex("STATE")));;
            cursor.close();
        }
        db.close();
        return taskItem;
    }
}
