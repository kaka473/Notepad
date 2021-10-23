package com.example.notepad.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.notepad.DBManager;
import com.example.notepad.EditActivity;
import com.example.notepad.MainActivity;
import com.example.notepad.NoteAdapter;
import com.example.notepad.NoteItem;
import com.example.notepad.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class noteFragment extends Fragment {

    private static final String TAG = "noteFragment";
    FloatingActionButton btn;
    TextView v1;
    ListView l1;
    private NoteAdapter adapter;
    private List<NoteItem> noteItemList=new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public noteFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static noteFragment newInstance(String param1, String param2) {
        noteFragment fragment = new noteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_note, container, false);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn=v.findViewById(R.id.fla1);
        Log.i(TAG, "kkkk: ");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "clickkkk: ");
                Intent intent=new Intent(getActivity(),EditActivity.class);
                startActivityForResult(intent,1001);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String content=data.getStringExtra("content");
        String time = data.getExtras().getString("time");
        NoteItem newNote = new NoteItem(content,time);
        DBManager db = new DBManager(getContext());
        db.add(newNote);
        noteItemList.addAll(db.listAll());

    }

    public void refreshListView() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        DBManager db = new DBManager(getContext());

        // set adapter
        if (noteItemList.size() > 0) noteItemList.clear();

        adapter.notifyDataSetChanged();

    }

}