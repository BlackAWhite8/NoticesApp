package com.example.noticesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button add;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> noticesList;
    private ListView listView;
    private int pos;
    private int len;
    SharedPreferences prefs, archivePrefs;
    SharedPreferences.Editor editor, archiveEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.ListView);
        add = findViewById(R.id.createButton);
        archivePrefs = getSharedPreferences("archive", Context.MODE_PRIVATE);
        archiveEditor = archivePrefs.edit();
        prefs = getSharedPreferences("notices_text", Context.MODE_PRIVATE);
        editor = prefs.edit();
        noticesList = new ArrayList<>();
        len = prefs.getInt("ListLength", 0);
        for(int i = 0; i < len ;i++) {
            noticesList.add(prefs.getString("NoticeText"+i, ""));
        }

        adapter = new ArrayAdapter<>(this, R.layout.notices_list_item, noticesList);
        add.setOnClickListener(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent addMenu = new Intent(MainActivity.this, ChangeNoticeActivity.class);
                pos = position;
                addMenu.putExtra("text", noticesList.get(position));
                startActivityForResult(addMenu, 2);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int archLen = archivePrefs.getInt("length", 0);
                archLen += 1;
                archiveEditor.putInt("length", archLen);
                archiveEditor.putString("item"+(archLen-1),noticesList.get(position));
                archiveEditor.apply();
                noticesList.remove(position);
                adapter.notifyDataSetChanged();
                Toast toast = Toast.makeText(getApplicationContext(),"this note was moved to the archive", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createButton:
                Intent createMenu = new Intent(MainActivity.this, CreateNoticeActivity.class);
                startActivityForResult(createMenu, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode==RESULT_OK) {
                final String noteText = data.getStringExtra("note");
                noticesList.add(0, noteText);
                adapter.notifyDataSetChanged();
            }
        }
        else if (requestCode == 2) {
            if (resultCode==RESULT_OK) {
                boolean isDelFlag = data.getBooleanExtra("flag", false);
                if(!isDelFlag) {
                    String noteText = data.getStringExtra("changedNote");
                    noticesList.set(pos, noteText);
                }
                else {
                    noticesList.remove(pos);
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
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.arch_button:
                Intent archiveMenu = new Intent(MainActivity.this, ArchiveActivity.class);
                startActivityForResult(archiveMenu, 3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        editor.putInt("ListLength", noticesList.size());
        for(int i = 0; i < noticesList.size(); i++) {
            editor.putString("NoticeText"+i, noticesList.get(i));
        }
        editor.apply();
    }
}

