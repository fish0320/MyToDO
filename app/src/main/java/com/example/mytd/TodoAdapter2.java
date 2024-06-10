package com.example.mytd;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class TodoAdapter2 extends SimpleAdapter{

    public TodoAdapter2(Context context, List<? extends HashMap<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        final TextView text = (TextView)v.findViewById(R.id.todo_name);
        text.setTag(position);
        Button btn = (Button)v.findViewById(R.id.btn_done);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Tag", "您点击了第"+v.getTag()+"个按钮:"+text.getText());
                //Intent intent = new Intent(this,FinishDetail.class);
                //startActivity(intent);
            }
        });

        return v;
    }

}


