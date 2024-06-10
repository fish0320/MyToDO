package com.example.mytd;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskListActivity extends ListActivity {
    private static final String TAG = "TaskListActivity";

    Handler handler;
    private ArrayList<HashMap<String, String>> listItems;
    private SimpleAdapter listItemAdapter;
    private int msgWhat = 7;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: 运行成功");
        initListView2();
        Log.i(TAG, "initlist: 列表设置");
        this.setListAdapter(listItemAdapter);
    }

    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemName", "Task name");
            map.put("itemStart", "From" + i);
            map.put("itemTimes", "已完成" + i + "天");
            listItems.add(map);
        }

        listItemAdapter = new SimpleAdapter(this, listItems,
                R.layout.tasklist_item, // ListItem的XML布局实现
                new String[]{"itemName", "itemStart", "itemTimes"},
                new int[]{R.id.itemName, R.id.itemStart, R.id.itemTimes}
        );

    }

    private void initListView2() {
        DBManager db = new DBManager(TaskListActivity.this);
        listItems = new ArrayList<HashMap<String,String>>();
        listItems = db.listTask();

        listItemAdapter = new SimpleAdapter(this, db.listTask(),
                R.layout.tasklist_item,
                new String[]{"itemName", "itemStart", "itemTimes"},
                new int[]{R.id.itemName, R.id.itemStart, R.id.itemTimes}
        );

    }

}


