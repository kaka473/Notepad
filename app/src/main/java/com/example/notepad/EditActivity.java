package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText t1;
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
            intent.putExtra("input",t1.getText().toString());
            setResult(1001,intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }


}