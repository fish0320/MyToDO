package com.example.mytd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class MainActivity_2 extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    TextView date;
    TextView weeknum;

    ImageButton btn_add;
    ImageButton btn_menu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTime();
        initListView();
        btn_add = findViewById(R.id.btn_to_add);
        btn_menu = findViewById(R.id.btn_menu);
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

    }
    private void initListView() {
        ListView list = findViewById(R.id.todo_list);
        List<HashMap<String, String>> items = new ArrayList<HashMap<String,String>>();
        DBManager db = new DBManager(MainActivity_2.this);
        items = new ArrayList<HashMap<String,String>>();
        items = db.listAllTodo();
        final TodoAdapter2 adapter = new TodoAdapter2(MainActivity_2.this, items,
                R.layout.todolist_item, new String[]{"itemName"}, new int[]{R.id.todo_name});
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                adapter.getView(arg2, arg1, arg0);
                @SuppressWarnings("unchecked")
                HashMap<String, String> map = (HashMap<String, String>)arg0.getItemAtPosition(arg2);
                Log.i(TAG, "onItemClick: 您点击了"+map.get("itemName"));
                Toast.makeText(MainActivity_2.this, "您点击了"+map.get("itemName"), Toast.LENGTH_LONG).show();
            }

        });

    }

}