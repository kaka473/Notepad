package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {
    EditText content;
    Toolbar toolbar;
    ImageView check;
    TextView title,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        content=findViewById(R.id.content);
        toolbar=findViewById(R.id.toolbar);
        title=findViewById(R.id.title);
        time=findViewById(R.id.time);
        time.setText(TimeExc());
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(10010);
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_save:
                        Intent intent=getIntent();
                        intent.putExtra("title",title.getText().toString());
                        intent.putExtra("content",content.getText().toString());
                        intent.putExtra("time",TimeExc());
                        setResult(10001,intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        }
        @Override
        public boolean onCreatePanelMenu(int featureId, Menu menu) {
            getMenuInflater().inflate(R.menu.toolbar_menu, menu);//toolbar添加menu菜单
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
            setResult(10010);
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