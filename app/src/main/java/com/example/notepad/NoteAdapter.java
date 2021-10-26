package com.example.notepad;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter{

    private Context mContext;

    private List<NoteItem> backList;//用来备份原始数据
    private List<NoteItem> noteList;//这个数据是会改变的，所以要有个变量来备份一下原始数据


    public NoteAdapter(Context mContext, List<NoteItem> noteList) {
        this.mContext = mContext;
        this.noteList = noteList;
        backList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.list_item, null);
        TextView tv_content = (TextView)v.findViewById(R.id.notecontent);
        TextView tv_time = (TextView)v.findViewById(R.id.notetime);

        tv_content.setText(noteList.get(position).getContent());
        tv_time.setText(noteList.get(position).getTime());
        return v;
    }
}
