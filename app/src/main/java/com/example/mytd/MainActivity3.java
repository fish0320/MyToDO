package com.example.mytd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    private static final String TAG = "MainActivity3";
    ImageButton btn_back;
    ListView listView;
    DoneAdapter adapter;
    ArrayList<DoneItem> listItems;
    String name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        name = intent.getStringExtra("taskname");
        TextView title = findViewById(R.id.doneTask_title);
        title.setText(name);

        btn_back = findViewById(R.id.btn_backto_list);
        listView = findViewById(R.id.done_showlist);
        Log.i(TAG, "onCreate: 开始设置列表");
        initListView();
        Log.i(TAG, "onCreate: 设置列表");
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.noDetail));
        Log.i(TAG, "onCreate: 设置成功");

    }

    private void initListView() {
        DBManager db = new DBManager(MainActivity3.this);
        listItems = new ArrayList<DoneItem>();
        listItems = db.listDoneItem(name);
        adapter = new DoneAdapter(MainActivity3.this,R.layout.donelist_item, listItems);
    }

    public void backClick(View btn_back){
        //Intent intent = new Intent(this, MainActivity2.class);
        //startActivity(intent);
        finish();
    }

}