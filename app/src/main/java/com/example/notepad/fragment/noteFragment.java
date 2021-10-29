package com.example.notepad.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.notepad.DBManager;
import com.example.notepad.EditActivity;
import com.example.notepad.EditActivity2;
import com.example.notepad.MainActivity;
import com.example.notepad.NoteAdapter;
import com.example.notepad.NoteItem;
import com.example.notepad.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class noteFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private static final String TAG = "noteFragment";
    FloatingActionButton btn;
    TextView v1;
    ListView l1;
    View v;
    private NoteAdapter adapter;
    private List<NoteItem> noteItemList=new ArrayList<>();

    public noteFragment() {
        // Required empty public constructor
    }

    public static noteFragment newInstance(String param1, String param2) {
        noteFragment fragment = new noteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_note, container, false);
        l1=v.findViewById(R.id.list1);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DBManager db = new DBManager(getContext());
        noteItemList.addAll(db.listAll());
        adapter=new NoteAdapter(getContext(),noteItemList);
        l1.setAdapter(adapter);

        btn=v.findViewById(R.id.fla1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),EditActivity.class);
                startActivityForResult(intent,1001);
            }
        });
        l1.setOnItemClickListener(this);
        l1.setOnItemLongClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)
        {
            refreshListView();
            return;
        }
        else {
            String title = data.getExtras().getString("title");
            String content = data.getExtras().getString("content");
            String time = data.getExtras().getString("time");
            if (requestCode == 1001&&resultCode==10001) {
                NoteItem newNote = new NoteItem(title, content, time);
                DBManager db = new DBManager(getContext());
                db.add(newNote);
            } else if (requestCode == 1002 && resultCode == 10002) {
                long id = data.getExtras().getLong("id");
                Log.i(TAG, "updateeee "+id);
                NoteItem newNote = new NoteItem(title, content, time);
                newNote.setId(id);
                DBManager db = new DBManager(getContext());
                db.update(newNote);
            }
        }
        refreshListView();
    }

    public void refreshListView() {
        DBManager db = new DBManager(getContext());
        if (noteItemList.size()>0) noteItemList.clear();
        noteItemList.addAll(db.listAll());
        adapter=new NoteAdapter(getContext(),noteItemList);
        l1.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.i(TAG, "onItemClick: an");
        Object itemAtPositon=l1.getItemAtPosition(position);
        NoteItem noteItem=(NoteItem)itemAtPositon;
        Intent edit=new Intent(getActivity(), EditActivity2.class);
        edit.putExtra("title",noteItem.getTitle());
        edit.putExtra("content",noteItem.getContent());
        edit.putExtra("id",noteItem.getId());
        edit.putExtra("time",noteItem.getTime());
        startActivityForResult(edit,1002);
    }
    public boolean onItemLongClick(AdapterView<?> parent, View view,int position,long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示").setMessage("请确认是否删除当前笔记").setPositiveButton("是",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Object itemAtPositon=l1.getItemAtPosition(position);
                NoteItem newNote=(NoteItem)itemAtPositon;
                Log.i(TAG, "iddddd "+newNote.getId());
                DBManager db = new DBManager(getContext());
                db.delete(newNote);
                refreshListView();
            }
        }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }

}