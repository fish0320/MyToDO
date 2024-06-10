package com.example.mytd;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTask extends AppCompatActivity {
    private static final String TAG = "AddTask";

    EditText taskname;
    EditText content;
    TextView selectedTime;
    TextView endTime;

    Button btn_select;
    Button btn_selectEnd;
    Button btn_add;
    ImageButton btn_return;
    String Date;
    String startDate;
    String endDate;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btn_select = findViewById(R.id.btn_select);
        btn_selectEnd = findViewById(R.id.btn_selectEnd);
        btn_select.setOnClickListener(new MyOnClickListener());
        btn_selectEnd.setOnClickListener(new MyOnClickListener());
        btn_add = findViewById(R.id.btn_addtask);
        btn_return = findViewById(R.id.btn_return);

        Date = getCurrentDate();
        selectedTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        selectedTime.setText(Date);
        endTime.setText(Date);
        startDate = selectedTime.getText().toString();
        endDate = endTime.getText().toString();
    }
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_select){
                int year, month, day;
                String[] dateParts = startDate.split("-");
                year = Integer.parseInt(dateParts[0]);
                month = Integer.parseInt(dateParts[1]) - 1;
                day = Integer.parseInt(dateParts[2]);
                DatePickerDialog pickerDialog = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = String.format("%d-%d-%d",year,month+1,dayOfMonth);
                        selectedTime.setText(date);
                    }
                },year,month,day);
                pickerDialog.show();
            }else if(v.getId() == R.id.btn_selectEnd){
                int year, month, day;
                String[] dateParts = endDate.split("-");
                year = Integer.parseInt(dateParts[0]);
                month = Integer.parseInt(dateParts[1]) - 1;
                day = Integer.parseInt(dateParts[2]);
                DatePickerDialog pickerDialog2 = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = String.format("%d-%d-%d",year,month+1,dayOfMonth);
                        endTime.setText(date);
                    }
                },year,month,day);
                pickerDialog2.show();
            }
        }

    }
    public void add(View btn_add){
        taskname = findViewById(R.id.task_name);
        taskname.setInputType(InputType.TYPE_CLASS_TEXT);
        taskname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(taskname.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(), "任务名称不能为空", Toast.LENGTH_SHORT).show();
        }else{
        content = findViewById(R.id.task_content);
        selectedTime = findViewById(R.id.start_time);
        TaskItem item = new TaskItem();
        item.setName(taskname.getText().toString());
        item.setDetail(content.getText().toString());
        item.setStart(selectedTime.getText().toString());
        item.setTimes(0);
        item.setState(endTime.getText().toString());
        DBManager dbManager = new DBManager(AddTask.this);
        dbManager.add(item);
        Log.i(TAG,"新增任务："+item.getName());
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        }
    }

    public void returnClick(View btn_return){
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
       finish();
    }
}
