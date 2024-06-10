package com.example.mytd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DoneAdapter extends ArrayAdapter {
    public DoneAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DoneItem> objects) {
        super(context,resource,objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        DoneItem listItem = (DoneItem) getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.donelist_item,parent,false);
        TextView itemTime = view.findViewById(R.id.doneTime);
        TextView itemContent = view.findViewById(R.id.doneContent);
        itemTime.setText(listItem.getTime());
        itemContent.setText(listItem.getContent());
        return view;
    }
}
