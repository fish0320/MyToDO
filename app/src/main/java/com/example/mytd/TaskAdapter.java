package com.example.mytd;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter{
    private OnItemClickListener onItemClickListener;
    public TaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ListItem> objects) {
        super(context,resource,objects);
    }
    public TaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ListItem> objects, OnItemClickListener onItemClickListener) {
        super(context, resource, objects);
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ListItem listItem = (ListItem) getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.tasklist_item,parent,false);
        TextView itemName = view.findViewById(R.id.itemName);
        TextView itemStart = view.findViewById(R.id.itemStart);
        TextView itemTimes = view.findViewById(R.id.itemTimes);
        itemName.setText(listItem.getName());
        itemStart.setText(listItem.getStart());
        itemTimes.setText(listItem.getTimes());

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton btn = (ImageButton)view.findViewById(R.id.btn_edit);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick((Integer) v.getTag());
                }
            }
        });
        return view;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
