package com.example.noticesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.TimeUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ArchiveActivity extends AppCompatActivity  {
    SharedPreferences archivePrefs, prefs;
    SharedPreferences.Editor archiveEditor, editor;
    int len, pos;
    Intent data = new Intent();
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> noticesList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archive_notice_activity);
        listView = findViewById(R.id.ListView2);
        archivePrefs = getSharedPreferences("archive", Context.MODE_PRIVATE);
        archiveEditor = archivePrefs.edit();
        prefs = getSharedPreferences("notices_text", Context.MODE_PRIVATE);
        editor = prefs.edit();
        len = archivePrefs.getInt("length",0);
        for(int i = 0; i < len; i ++) {
            noticesList.add(archivePrefs.getString("item"+i,""));
        }

        adapter = new ArrayAdapter<>(this, R.layout.notices_list_item, noticesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent changeNote = new Intent(ArchiveActivity.this, ChangeNoticeActivity.class);
                pos = position;
                changeNote.putExtra("text", noticesList.get(position));
                startActivityForResult(changeNote, 1);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int archLen = prefs.getInt("ListLength", 0);
                archLen += 1;
                editor.putInt("ListLength", archLen);
                editor.putString("NoticeText"+(archLen-1),noticesList.get(position));
                editor.apply();
                for(int i = position; i < noticesList.size()-1; i++) {
                    archiveEditor.putString("item"+i, noticesList.get(i+1));
                }
                noticesList.remove(position);
                archiveEditor.putInt("length", noticesList.size());
                archiveEditor.apply();
                adapter.notifyDataSetChanged();
                Toast toast = Toast.makeText(getApplicationContext(),"this note was moved back to the main menu", Toast.LENGTH_LONG);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.setEnabled(true);
                    }
                },1000);
                listView.setEnabled(false);
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                boolean isDelFlag = data.getBooleanExtra("flag", false);
                if(!isDelFlag) {
                    String noteText = data.getStringExtra("changedNote");
                    archiveEditor.putString("item"+pos, noteText);
                    archiveEditor.apply();
                    noticesList.set(pos, noteText);
                }
                else {
                    for(int i = pos; i < noticesList.size()-1; i++) {
                        archiveEditor.putString("item"+i, noticesList.get(i+1));
                    }
                    noticesList.remove(pos);
                    archiveEditor.putInt("length", noticesList.size());
                    archiveEditor.apply();
                }
                adapter.notifyDataSetChanged();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.archive_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_arch_button:
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(RESULT_OK);
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
