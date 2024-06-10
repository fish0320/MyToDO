package com.example.mytd;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskListActivity2 extends ListActivity {

    Handler handler;
    private ArrayList<HashMap<String, String>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter;
    private int msgWhat = 7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_task_list2);
        initListView2();
        this.setListAdapter(listItemAdapter);
    }

    private void initListView2() {
        DBManager db = new DBManager(TaskListActivity2.this);
        listItems = new ArrayList<HashMap<String,String>>();
        listItems = db.listTask();

        listItemAdapter = new SimpleAdapter(this, db.listTask(),
                R.layout.tasklist_item,
                new String[]{"itemName", "itemStart", "itemTimes"},
                new int[]{R.id.itemName, R.id.itemStart, R.id.itemTimes}
        );
    }
}