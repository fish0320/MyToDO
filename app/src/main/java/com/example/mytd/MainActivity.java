package com.example.mytd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements TodoAdapter.InnerItemOnclickListener,
        AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    TextView date;
    TextView weeknum;

    ImageButton btn_add;
    ImageButton btn_menu;

    ListView list;
    List<HashMap<String, String>> items;
    String todoname="";
    TextView all;
    TextView done;

    TodoAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTime();
        initListView();
        btn_add = findViewById(R.id.btn_to_add);
        btn_menu = findViewById(R.id.btn_menu);
        all = findViewById(R.id.task_done);
        done = findViewById(R.id.task_undo);
        int itemCount = list.getAdapter().getCount();
        String doneText = done.getText().toString();
        int Num = Integer.parseInt(doneText)+itemCount;
        String count = Integer.toString(Num);
        all.setText(count);
    }

    public void getTime(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String month = String.valueOf(c.get(Calendar.MONTH)+1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String year = String.valueOf(c.get(Calendar.YEAR));
        if(Integer.parseInt(day)>MaxDayFromDay_OF_MONTH(Integer.parseInt(year),Integer.parseInt(month))){
            day = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(year),Integer.parseInt(month)));
        }
        date = (TextView)findViewById(R.id.date_day);
        date.setText(month+"月"+day+"日");
        String time = "";
        if(Integer.parseInt(month)<10){
            time =year+"-0"+month+"-"+day;
        }else {
            time = year + "-" + month + "-" + day;
        }
        String Week = getWeek(time);
        weeknum = (TextView) findViewById(R.id.date_week);
        weeknum.setText(Week);
    }

    private int MaxDayFromDay_OF_MONTH(int year,int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month-1);
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);
        return day;
    }

    public String getWeek(String time){
        String week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try{
            c.setTime(format.parse(time));
        }catch (ParseException e){
            e.printStackTrace();
        }
        if(c.get(Calendar.DAY_OF_WEEK)==1){
            week+="Sun.周日";
        }if(c.get(Calendar.DAY_OF_WEEK)==2){
            week+="Mon.周一";
        }if(c.get(Calendar.DAY_OF_WEEK)==3){
            week+="Tues.周二";
        }if(c.get(Calendar.DAY_OF_WEEK)==4){
            week+="Wed.周三";
        }if(c.get(Calendar.DAY_OF_WEEK)==5){
            week+="Thur.周四";
        }if(c.get(Calendar.DAY_OF_WEEK)==6){
            week+="Fri.周五";
        }if(c.get(Calendar.DAY_OF_WEEK)==7){
            week+="Sat.周六";
        }
        return  week;
    }

    public void addClick(View btn_add){
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }
    public void menuClick(View btn_menu){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    private void initListView() {
        list = findViewById(R.id.todo_list);
        items = new ArrayList<HashMap<String,String>>();
        DBManager db = new DBManager(MainActivity.this);
        items = new ArrayList<HashMap<String,String>>();
        items = db.listAllTodo();
        adapter = new TodoAdapter(items, this);
        adapter.setOnInnerItemOnClickListener((TodoAdapter.InnerItemOnclickListener) this);
        list.setAdapter(adapter);
        list.setEmptyView(findViewById(R.id.noTodo));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("整体item----->", position + "error");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        Log.e("内部item--1-->", position + "error");
        HashMap<String, String> itemData = items.get(position);
        doneUpdate(itemData.get("itemName"));

        adapter.removeItem(position);

        Intent intent = new Intent(this,FinishDetail.class);
        intent.putExtra("taskName",itemData.get("itemName"));
        Log.i(TAG, "itemClick: "+ itemData.get("itemName"));
        startActivityForResult(intent,1);
    }
    @SuppressLint("SetTextI18n")
    public void doneUpdate(String taskName){
        DBManager db = new DBManager(MainActivity.this);
        TaskItem taskItem = new TaskItem();
        taskItem = db.findByNAME(taskName);
        int times = taskItem.getTimes()+1;
        taskItem.setTimes(times);
        db.update(taskItem);

        String doneText = done.getText().toString();
        int doneNum = Integer.parseInt(doneText)+1;
        done.setText(Integer.toString(doneNum));

    }
}