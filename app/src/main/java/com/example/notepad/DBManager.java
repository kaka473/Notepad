package com.example.notepad;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager{
    private static final String TAG = "DBManager";
    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(NoteItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", item.getTitle());
        values.put("CONTENT", item.getContent());
        values.put("TIME", item.getTime());
        db.insert(TBNAME, null, values);
        Log.i(TAG, "add: "+"doooo");
        db.close();
    }

    public void delete(NoteItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(item.getId())});
        Log.i(TAG, "deleteeee: ");
        db.close();
    }

    public void update(NoteItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", item.getTitle());
        values.put("CONTENT", item.getContent());
        values.put("TIME", item.getTime());
        db.update(TBNAME, values, "ID=?",new String[]{String.valueOf(item.getId())});
        Log.i(TAG, "update: succcc");
        db.close();
    }

    @SuppressLint("Range")
    public List<NoteItem> listAll(){
        List<NoteItem> notesList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            notesList = new ArrayList<NoteItem>();
            while(cursor.moveToNext()){
                NoteItem item = new NoteItem();
                item.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                item.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
                item.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));
                item.setTime(cursor.getString(cursor.getColumnIndex("TIME")));
                notesList.add(item);
            }
            cursor.close();
        }
        db.close();
        return notesList;
    }

    @SuppressLint("Range")
    public NoteItem findById(long id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        NoteItem noteItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            noteItem = new NoteItem();
            noteItem.setId(cursor.getLong(cursor.getColumnIndex("ID")));
            noteItem.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
            noteItem.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));
            noteItem.setTime(cursor.getString(cursor.getColumnIndex("TIME")));
            cursor.close();
        }
        db.close();
        return noteItem;
    }
}