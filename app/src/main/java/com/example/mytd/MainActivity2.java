package com.example.mytd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    ListView listView;
    private ArrayList<ListItem> listItems;
    TaskAdapter adapter;
    ImageButton btn_back;
    FloatingActionButton btn_floatadd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn_back = findViewById(R.id.btn_openMenu);
        btn_floatadd = findViewById(R.id.btn_float_add);
        listView = findViewById(R.id.task_showlist);
        Log.i(TAG, "onCreate: 开始设置列表");
        initListView2();
        Log.i(TAG, "onCreate: 设置列表");

        Log.i(TAG, "onCreate: 设置成功");
    }

    @Override
    public void onResume() {
        super.onResume();
        initListView2();
    }
    public void menuClick(View btn_back){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void floatadd(View btn_floatadd){
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }
    private void initListView2() {
        DBManager db = new DBManager(MainActivity2.this);
        listItems = new ArrayList<ListItem>();
        listItems = db.listAllItem();
        //adapter = new TaskAdapter(MainActivity2.this,R.layout.tasklist_item,listItems);
        adapter = new TaskAdapter(this, R.layout.tasklist_item, listItems, new TaskAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                ListItem itemData = listItems.get(position);
                String taskName = itemData.getName();
                Intent intent = new Intent(MainActivity2.this, EditActivity.class);
                intent.putExtra("taskname", taskName);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.noTask));
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ListItem itemData = listItems.get(position);
               String taskName = itemData.getName();
               Log.i(TAG, "onItemClick: 点击"+taskName);
               Intent intent2 = new Intent(MainActivity2.this, MainActivity3.class);
               intent2.putExtra("taskname", taskName);
               startActivity(intent2);
           }
       });
    }
}