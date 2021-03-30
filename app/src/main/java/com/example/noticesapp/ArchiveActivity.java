package com.example.noticesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ArchiveActivity extends AppCompatActivity  {
    SharedPreferences archivePrefs;
    SharedPreferences.Editor archiveEditor;
    int len;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> noticesList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archive_notice_activity);
        listView = findViewById(R.id.ListView2);
        archivePrefs = getSharedPreferences("archive", Context.MODE_PRIVATE);
        archiveEditor = archivePrefs.edit();
        len = archivePrefs.getInt("length",0);
        for(int i = 0; i < len; i ++) {
            noticesList.add(archivePrefs.getString("item"+i,""));
        }
        adapter = new ArrayAdapter<>(this, R.layout.notices_list_item, noticesList);
        listView.setAdapter(adapter);
    }
}
