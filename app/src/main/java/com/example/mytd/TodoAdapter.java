package com.example.mytd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class TodoAdapter extends BaseAdapter implements View.OnClickListener {
    private List<HashMap<String,String>> mList;
    private Context mContext;
    private InnerItemOnclickListener mListener;
    public TodoAdapter(List<HashMap<String,String>> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }
   // public TodoAdapter(Context context, List<? extends HashMap<String, ?>> data, int resource, String[] from, int[] to) {
    //super(context, data, resource, from, to);
    //}

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public final static class ViewHolder {
        Button bt1;
        TextView tv;
    }

    interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.todolist_item,
                    null);
            viewHolder.bt1 = (Button) convertView.findViewById(R.id.btn_done);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.todo_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bt1.setOnClickListener(this);
        viewHolder.bt1.setTag(position);
        String str = mList.get(position).toString();
        int len = str.length();
        String str1 = str.substring(10,len-1);
        viewHolder.tv.setText(str1);
        return convertView;
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
        this.mListener=listener;
    }
    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
}
