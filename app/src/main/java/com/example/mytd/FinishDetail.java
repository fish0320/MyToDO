package com.example.mytd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FinishDetail extends AppCompatActivity {
    private static final String TAG = "FinishDetail";
    Button addDetail;
    TextView taskname;
    TextView content;
    TextView date;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_detail);
        Intent intent = getIntent();
        String taskname = intent.getStringExtra("taskName");
        Log.i(TAG, "onCreate: ");
        TextView Taskname = findViewById(R.id.detail_name);
        Taskname.setText(taskname);
        TextView Date = findViewById(R.id.detail_time);
        Date.setText("完成时间：" + getCurrentDate());
        addDetail = findViewById(R.id.btn_adddetail);
    }
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    public void add(View addDetail){
        taskname = findViewById(R.id.detail_name);
        content = findViewById(R.id.detail_content);
        date = findViewById(R.id.detail_time);
        DetailItem item = new DetailItem();
        item.setName(taskname.getText().toString());
        if(content.getText().toString().length()==0){
            item.setContent("无");
        }else{
            item.setContent(content.getText().toString());
        }
        item.setDate(getCurrentDate());
        DBManager dbManager = new DBManager(FinishDetail.this);
        dbManager.addDetail(item);
        Log.i(TAG,"新增任务："+item.getName());
        Toast.makeText(FinishDetail.this, "记录已保存", Toast.LENGTH_SHORT).show();
        finish();
    }
}