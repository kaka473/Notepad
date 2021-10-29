package com.example.notepad.Todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
            View v = View.inflate(mContext, R.layout.todo_list, null);
            TextView content = v.findViewById(R.id.todocontent);
            TextView time = v.findViewById(R.id.todotime);
            content.setText(todoList.get(position).getContent());
            time.setText(todoList.get(position).getTime());
            return v;
        }


}
