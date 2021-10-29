package com.example.notepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity2 extends AppCompatActivity {

    private static final String TAG = "EditActivity2";
    EditText content;
    Toolbar toolbar;
    ImageView check;
    TextView title,time;
    long id;
    String mtitle,mcontent,mtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);
        content=findViewById(R.id.content1);
        toolbar=findViewById(R.id.toolbar1);
        title=findViewById(R.id.title1);
        time=findViewById(R.id.time1);
        time.setText(TimeExc());
        Intent intent=getIntent();
        id=intent.getLongExtra("id",0);
        Log.i(TAG, "gei id "+id);
        mtitle=intent.getStringExtra("title");
        mcontent=intent.getStringExtra("content");
        mtime=intent.getStringExtra("time");
        title.setText(mtitle);
        content.setText(mcontent);
        content.setSelection(mcontent.length());
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(10000);
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_save1:
                        Log.i(TAG, "clickkkkkkk");
                        Intent intent=getIntent();
                        intent.putExtra("id",id);
                        intent.putExtra("title",title.getText().toString());
                        intent.putExtra("content",content.getText().toString());
                        intent.putExtra("time",TimeExc());
                        setResult(10002,intent);
                        finish();
                        break;
                    case R.id.action_delete1:
                        NoteItem newNote = new NoteItem(mtitle,mcontent,mtime);
                        newNote.setId(id);
                        Log.i(TAG, "iddddd "+id);
                        DBManager db = new DBManager(getApplicationContext());
                        db.delete(newNote);
                        Toast.makeText(getApplicationContext(),"已删除笔记",Toast.LENGTH_LONG).show();
                        setResult(10003);
                        finish();
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_one_menu, menu);//toolbar添加menu菜单

        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_HOME)
        {
            return true;
        }
        else if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            setResult(10000);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    public String TimeExc()
    {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}