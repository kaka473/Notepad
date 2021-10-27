package com.example.notepad.Todo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.notepad.DBHelper;
import com.example.notepad.Todo.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class TDBManager {
        private static final String TAG = "TDBManager";
        private DBHelper dbHelper;
        private String TB1NAME;

        public TDBManager(Context context) {
            dbHelper = new DBHelper(context);
            TB1NAME = DBHelper.TB1_NAME;
        }

        public void add(TodoItem item){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("CONTENT", item.getContent());
            values.put("TIME", item.getTime());
            db.insert(TB1NAME, null, values);
            Log.i(TAG, "add: "+"doooo");
            db.close();
        }

        public void delete(TodoItem item){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TB1NAME, "ID=?", new String[]{String.valueOf(item.getId())});
            Log.i(TAG, "deleteeee: ");
            db.close();
        }

        public void update(TodoItem item){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("CONTENT", item.getContent());
            values.put("TIME", item.getTime());
            db.update(TB1NAME, values, "ID=?",new String[]{String.valueOf(item.getId())});
            Log.i(TAG, "update: succcc");
            db.close();
        }

        @SuppressLint("Range")
        public List<TodoItem> listAll(){
            List<TodoItem> todosList = null;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TB1NAME, null, null, null, null, null, null);
            if(cursor!=null){
                todosList = new ArrayList<TodoItem>();
                while(cursor.moveToNext()){
                    TodoItem item = new TodoItem();
                    item.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                    item.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));
                    item.setTime(cursor.getString(cursor.getColumnIndex("TIME")));
                    todosList.add(item);
                }
                cursor.close();
            }
            db.close();
            return todosList;
        }

        @SuppressLint("Range")
        public TodoItem findById(long id){
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TB1NAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
            TodoItem TodoItem = null;
            if(cursor!=null && cursor.moveToFirst()){
                TodoItem = new TodoItem();
                TodoItem.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                TodoItem.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));
                TodoItem.setTime(cursor.getString(cursor.getColumnIndex("TIME")));
                cursor.close();
            }
            db.close();
            return TodoItem;
        }

}
