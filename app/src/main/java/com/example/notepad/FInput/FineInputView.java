package com.example.notepad.FInput;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.notepad.R;
import com.example.notepad.Todo.TDBManager;
import com.example.notepad.Todo.TodoAdapter;
import com.example.notepad.Todo.TodoItem;
import com.example.notepad.fragment.todoFragment;


import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author gexinyu
 */
public class FineInputView extends FrameLayout implements View.OnClickListener, View.OnKeyListener {

    private static final String TAG = "FineInputView";
    private Context mContext;
    private RelativeLayout rlSpecialContent;//存放特殊view的地方（可以自定义）
    private int touchSlop;//触点的位移用于校验是否是点击
    private long isClickTime = 350;//两次点击时间控制在为点击时间范围之内
    private long downTime;//按下时间
    private float downX, downY;//按下位置
    private OnInputListener onInputListener;
    private FragmentManager fm;

    private boolean mIsSpecial = false;//是否是特别页面
    Button  btnsave;
    ImageButton imgbtn,imgbtd;
    String date[]=new String[2];
    EditText edit;
    ListView l2;
    TextView cdate,ctime;

    TodoAdapter adapter;
    List<TodoItem> todoItemList=new ArrayList<>();

    public FineInputView(@NonNull Context context) {
        this(context, null);
    }

    public FineInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FineInputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "FineInputView: "+context);
        String currentdate=TimeExc();
        int i=0;
        StringTokenizer str=new StringTokenizer(currentdate);
        while(str.hasMoreTokens()) {
            date[i]=str.nextToken();
            i++;
        }
        mContext = context;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_fine_input, this);
        View v=LayoutInflater.from(context).inflate(R.layout.fragment_todo, this);
        l2=v.findViewById(R.id.list2);
        rlSpecialContent = contentView.findViewById(R.id.rl_special_content);
        btnsave = contentView.findViewById(R.id.btnsave);
        imgbtn=contentView.findViewById(R.id.imgb);
        imgbtd=contentView.findViewById(R.id.imgd);
        edit=contentView.findViewById(R.id.edit1);
        cdate=contentView.findViewById(R.id.cdate);
        ctime=contentView.findViewById(R.id.ctime);
        cdate.setText(date[0]);
        ctime.setText(date[1]);
        Log.i(TAG, "FineInputView: "+date[0]+date[1]);
        btnsave.setOnClickListener(this);
        imgbtn.setOnClickListener(this);
        imgbtd.setOnClickListener(this);

        getInputView().setOnKeyListener(this);
    }
    /**
     * 获取展示的view
     *
     * @return
     */
    public View getInputView() {
        return getChildAt(0);
    }
    /**
     * 初始化主要用于设置底部view的高度
     *
     * @param bottomHeight
     */
    public void initialize(int bottomHeight) {
        show();
        //设置为占空间
        rlSpecialContent.setVisibility(INVISIBLE);
        ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) rlSpecialContent.getLayoutParams();
        int lastHeight = linearParams.height;
        if (bottomHeight != lastHeight) {
            //不重复设置
            linearParams.height = bottomHeight;
            rlSpecialContent.setLayoutParams(linearParams);
        }
    }
    /**
     * dismiss之后重新显示 没销毁状态
     */
    public void show() {
        //获取焦点用于监听back键
        changeFocus(true);
        //添加视图到content中
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            final ViewGroup content = (ViewGroup) decorView.findViewById(android.R.id.content);
            if (getParent() == null) {
                content.addView(this);
            }
        }
    }
    /**
     * 取消
     */
    public void dismiss() {
        //获取焦点用于监听back键
        changeFocus(false);
        changeInputState(false);
        //移除掉当前的view
        ViewParent parent = getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(this);
        }
    }
    /**
     * 改变焦点
     *
     * @param isShow
     */
    public void changeFocus(boolean isShow) {
        if (isShow) {
            View inputView = getInputView();
            inputView.requestFocus();
            inputView.setFocusable(true);
            inputView.setFocusableInTouchMode(true);
        } else {
            clearFocus();
            View contentView = ((Activity) getContext()).findViewById(android.R.id.content);
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e("TAG", "dispatchKeyEvent");
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        Log.e("TAG", "dispatchKeyEventPreIme");
        return super.dispatchKeyEventPreIme(event);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (onInputListener != null) {
                onInputListener.onBackPressed();
            }
            return true;
        }
        return false;
    }
    /**
     * 处理点击范围，如果在不在就取消
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect rect = new Rect();
        getInputView().getGlobalVisibleRect(rect);
        if (!WindowUtil.touchIsInRect(event.getX(), event.getY(), rect)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    downTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_UP:
                    float dx = event.getX() - downX;
                    float dy = event.getY() - downY;
                    float distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                    if (distance < touchSlop && (System.currentTimeMillis() - downTime) < isClickTime) {
                        //点击外部就消失
                        onInputListener.onCanceledOnTouchOutside();
                    }
                    downX = 0;
                    downY = 0;
                    downTime = 0;
                    break;
            }
        }
        return true;
    }
    /**
     * 切换内容
     *
     * @param isSpecialContent
     */
    public void changeInputState(boolean isSpecialContent) {
        mIsSpecial = isSpecialContent;
        //布局的时候
        rlSpecialContent.setVisibility(isSpecialContent == true ? VISIBLE : INVISIBLE);
    }
    /**
     * 设置输入框的一些功能监听
     *
     * @param onInputListener
     */
    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }
    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        if(view==imgbtd)
        {
            final int[] Year = {calendar.get(Calendar.YEAR)};
            final int[] Month = {calendar.get(Calendar.MONTH)};
            final int[] Day = {calendar.get(Calendar.DAY_OF_MONTH)};
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            Year[0] = year;
                            Month[0] = month;
                            Day[0] = dayOfMonth;
                            final String data =  year+"-"+(month+1) + "-" + dayOfMonth;
                            cdate.setText(data);
                            date[0]=data;
                        }
                    },
                    Year[0], Month[0], Day[0]);
            datePickerDialog.show();
        }else if(view==imgbtn)
        {
            final int[] Hour = {calendar.get(Calendar.HOUR_OF_DAY)};
            final int[] Minute = {calendar.get(Calendar.MINUTE)};
            TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            Hour[0] =hour;
                            Minute[0] =minute;
                            final String data=hour+":"+minute+":"+"00";
                            ctime.setText(data);
                            date[1]=data;
                        }
                    },
                    Hour[0], Minute[0],true);
            timePickerDialog.show();
        }
        else if(view==btnsave)
        {
            String notecontent=edit.getText().toString();
            String time=date[0]+" "+date[1];
            TDBManager db = new TDBManager(getContext());
            TodoItem todoItem=new TodoItem(notecontent,time);
            db.add(todoItem);
            edit.setText("");
            onInputListener.onCanceledOnTouchOutside();
        }
    }
    public String TimeExc()
    {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
