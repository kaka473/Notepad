package com.example.notepad.Todo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notepad.NoteItem;
import com.example.notepad.R;

import java.util.List;

public class TodoAdapter extends BaseAdapter{
        private final Context mContext;
        private final List<TodoItem> todoList;

        public TodoAdapter(Context mContext, List<TodoItem> todoList) {
            this.mContext = mContext;
            this.todoList = todoList;
        }

        @Override
        public int getCount() {
            return todoList.size();
        }

        @Override
        public Object getItem(int position) {
            return todoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(mContext, R.layout.note_list, null);
            TextView tv_content = (TextView)v.findViewById(R.id.todocontent);
            tv_content.setText(todoList.get(position).getContent());
            return v;
        }


}
