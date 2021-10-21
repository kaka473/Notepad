package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.example.notepad.fragment.noteFragment;
import com.example.notepad.fragment.todoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity   implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    private static final String TAG = "MainActivity";
    ViewPager viewPager;
    BottomNavigationView mNavigationView;
    noteFragment  noteFragments = new noteFragment();
    todoFragment  todoFragments = new todoFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //页面初始化导航栏
        init();
    }

    private void init() {

        //获取页面标签对象
        viewPager = findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(this);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(this);


        //页面切换
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return  noteFragments;
                    case 1:
                        return  todoFragments;
                }

                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    //实现接口的相关方法  implements上面两个方法后 alt+enter就会弹出这些接口，直接回车实现他们
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        viewPager.setCurrentItem(menuItem.getOrder());
        Log.i(TAG, "running: "+menuItem.getOrder());
        return true;
    }
}



