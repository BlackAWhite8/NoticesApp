package com.example.noticesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button add;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> noticesList;
    private ListView listView;
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.ListView);
        add = findViewById(R.id.createButton);
        noticesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,R.layout.notices_list_item,noticesList);
        add.setOnClickListener(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent addMenu = new Intent(MainActivity.this, CreateNoticeActivity.class);
                pos = position;
                addMenu.putExtra("text",noticesList.get(position));
                startActivityForResult(addMenu,2);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createButton:
                Intent createMenu = new Intent(MainActivity.this, CreateNoticeActivity.class);
                startActivityForResult(createMenu,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode==RESULT_OK) {
                final String noteText = data.getStringExtra("note");
                noticesList.add(0,noteText);
                adapter.notifyDataSetChanged();
            }
        }
        else if (requestCode == 2) {
            if (resultCode==RESULT_OK) {
                String noteText = data.getStringExtra("note");
                noticesList.set(pos,noteText);
                adapter.notifyDataSetChanged();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

