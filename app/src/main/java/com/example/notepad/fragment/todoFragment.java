package com.example.notepad.fragment;

import static java.lang.Math.abs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.notepad.DBManager;
import com.example.notepad.EditActivity;
import com.example.notepad.EditActivity2;
import com.example.notepad.FInput.FineInput;
import com.example.notepad.FInput.KeyboardUtils;
import com.example.notepad.MainActivity;
import com.example.notepad.NoteAdapter;
import com.example.notepad.NoteItem;
import com.example.notepad.R;
import com.example.notepad.Todo.TDBManager;
import com.example.notepad.Todo.TodoAdapter;
import com.example.notepad.Todo.TodoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class todoFragment extends Fragment implements AdapterView.OnItemClickListener{

    private static final String TAG = "todoFragment";
    private FineInput fineInput;
    FloatingActionButton btn;
    View v,rv;
    ListView l2;
    ImageButton imgbtn;
    Button savebtn;
    private TodoAdapter adapter;
    private List<TodoItem> todoItemList=new ArrayList<>();
    public todoFragment() {
        // Required empty public constructor
    }
    public static todoFragment newInstance(String param1, String param2) {
        todoFragment fragment = new todoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TDBManager db = new TDBManager(getContext());
        todoItemList.addAll(db.listAll());
        adapter=new TodoAdapter(getContext(),todoItemList);
        l2.setAdapter(adapter);

        btn=v.findViewById(R.id.fla2);
        imgbtn=v.findViewById(R.id.imgb);
        savebtn=v.findViewById(R.id.btnsave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fineInput==null) {
                    fineInput = new FineInput(getActivity());
                }
                fineInput.showInput();
            }
        });
        Rect r = new Rect();
        rv.getWindowVisibleDisplayFrame(r);
        final int[] height = {r.height()};
        int height2=height[0];
        rv.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override public void onGlobalLayout() {
                        Rect r = new Rect();
                        rv.getWindowVisibleDisplayFrame(r);
                        int height1=r.height();
                        if (height[0] ==0) {
                            height[0] =height1;
                        }
                        if (height[0] -height1> height2/3) {
                            height[0] =height1;
                        }
                        if (height[0] -height1< height2/3) {
                            height[0] =height1;
                            refreshListView();
                        }
                      //  rv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                   }});
        l2.setOnItemClickListener(this);

    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        rv=activity.getWindow().getDecorView();
    }



    @Override
    public void onStart() {
        super.onStart();
        refreshListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshListView();
    }

    @Override
    public void onPause() {
        super.onPause();
        refreshListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_todo, container, false);
        l2=v.findViewById(R.id.list2);
        return v;
    }

    public void refreshListView() {
        TDBManager db = new TDBManager(getContext());
        if (todoItemList.size()>0) todoItemList.clear();
        todoItemList.addAll(db.listAll());
        adapter=new TodoAdapter(getContext(),todoItemList);
        l2.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示").setMessage("请确认是否删除当前待办").setPositiveButton("是",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Object itemAtPositon=l2.getItemAtPosition(position);
                TodoItem todoItem=(TodoItem)itemAtPositon;
                TDBManager db = new TDBManager(getContext());
                db.delete(todoItem);
                refreshListView();
            }
        }).setNegativeButton("否",null);
        builder.create().show();
    }

}