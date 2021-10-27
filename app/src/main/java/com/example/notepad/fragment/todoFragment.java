package com.example.notepad.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.notepad.EditActivity;
import com.example.notepad.FInput.FineInput;
import com.example.notepad.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;


public class todoFragment extends Fragment{

    private static final String TAG = "todoFragment";
    private FineInput fineInput;
    FloatingActionButton btn;
    View v;
    ImageButton imgbtn;
    Button savebtn;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_todo, container, false);
        return v;
    }

}