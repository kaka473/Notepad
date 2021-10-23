package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {
    EditText t1;
    private String content;
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        t1=findViewById(R.id.t1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_HOME)
        {
            return true;
        }
        else if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            Intent intent=getIntent();
            intent.putExtra("content",t1.getText().toString());
            intent.putExtra("time",TimeExc());
            setResult(1001,intent);
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