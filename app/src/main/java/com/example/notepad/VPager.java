package com.example.notepad;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VPager extends ViewPager {
    public VPager(@NonNull Context context) {
       super(context);
    }
    public VPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCurrentItem(int item){
        super.setCurrentItem(item,false);
    }
}
