package com.example.mytd;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    ImageButton btn_return;
    Button btn_edit;
    Button btn_delete;
    Button btn_select;
    Button btn_selectEnd;

    EditText editName;
    EditText editContent;
    TextView editStartTime;
    TextView editEndTime;
    int id;
    int times;
    String state;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        String taskname = intent.getStringExtra("taskname");

        DBManager db = new DBManager(EditActivity.this);
        TaskItem taskItem = new TaskItem();
        taskItem = db.findByNAME(taskname);

        editName = findViewById(R.id.editTask_name);
        editContent = findViewById(R.id.editTask_content);
        editStartTime = findViewById(R.id.editStart_time);
        editEndTime = findViewById(R.id.editEnd_time);
        editName.setText(taskItem.getName());
        editStartTime.setText(taskItem.getStart());
        editContent.setText(taskItem.getDetail());
        editEndTime.setText(taskItem.getState());

        editName.setInputType(InputType.TYPE_CLASS_TEXT);
        editName.addTextChangedListener(new TextWatcher() {
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

        id = taskItem.getId();
        times = taskItem.getTimes();

        btn_select = findViewById(R.id.btn_select_edit);
        btn_selectEnd = findViewById(R.id.btn_selectEnd_edit);
        btn_select.setOnClickListener(new EditActivity.MyOnClickListener());
        btn_selectEnd.setOnClickListener(new EditActivity.MyOnClickListener());
        btn_edit = findViewById(R.id.btn_editTask);
        btn_delete = findViewById(R.id.btn_deleteTask);
        btn_return = findViewById(R.id.btn_return_edit);
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_select_edit){
                String startDate = editStartTime.getText().toString();
                int year, month, day;
                String[] dateParts = startDate.split("-");
                year = Integer.parseInt(dateParts[0]);
                month = Integer.parseInt(dateParts[1]) - 1;
                day = Integer.parseInt(dateParts[2]);
                DatePickerDialog pickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = String.format("%d-%d-%d",year,month+1,dayOfMonth);
                        editStartTime.setText(date);
                    }
                },year,month,day);
                pickerDialog.show();
            }else if(v.getId() == R.id.btn_selectEnd_edit){
                String EndDate = editEndTime.getText().toString();
                int year, month, day;
                String[] dateParts = EndDate.split("-");
                year = Integer.parseInt(dateParts[0]);
                month = Integer.parseInt(dateParts[1]) - 1;
                day = Integer.parseInt(dateParts[2]);
                DatePickerDialog pickerDialog2 = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = String.format("%d-%d-%d",year,month+1,dayOfMonth);
                        editEndTime.setText(date);
                    }
                },year,month,day);
                pickerDialog2.show();
            }
        }

    }

    public void edit(View btn_edit){
        if(editName.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(), "任务名称不能为空", Toast.LENGTH_SHORT).show();
        }else{
        TaskItem item = new TaskItem();
        item.setName(editName.getText().toString());
        item.setDetail(editContent.getText().toString());
        item.setStart(editStartTime.getText().toString());
        item.setId(id);
        item.setTimes(times);
        item.setState(editEndTime.getText().toString());
        DBManager db2 = new DBManager(EditActivity.this);
        db2.update(item);
        Toast.makeText(EditActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
        finish();
        }
    }

    public void delete(View btn_delete){
        DBManager db3 = new DBManager(EditActivity.this);
        db3.delete(id);
        Toast.makeText(EditActivity.this, "任务已删除", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void returnClick(View btn_return){
        if(btn_return.getId() == R.id.btn_return_edit){
            //Intent intent = new Intent(this, MainActivity2.class);
            //startActivity(intent);
            finish();
        }
    }
}