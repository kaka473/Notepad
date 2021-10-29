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

    private List<NoteItem> noteList;

    public NoteAdapter(Context mContext, List<NoteItem> noteList) {
        this.mContext = mContext;
        this.noteList = noteList;
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
        TextView tv_content =v.findViewById(R.id.notecontent);
        TextView tv_time =v.findViewById(R.id.notetime);

        tv_content.setText(noteList.get(position).getTitle());
        tv_time.setText(noteList.get(position).getTime());
        return v;
    }
}
