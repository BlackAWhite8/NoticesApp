package com.example.noticesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNoticeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText textGetter;
    private Button done;
    private String noticetext;
    Intent data = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_create_activity);
        textGetter = findViewById(R.id.noticeField);
        if (getIntent().getStringExtra("text") != null) {
            noticetext = getIntent().getStringExtra("text");
            textGetter.setText(noticetext);
        }
        done = findViewById(R.id.doneButton);
        done.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doneButton:
                data.putExtra("note",textGetter.getText().toString());
                setResult(RESULT_OK,data);
                finish();
                break;
        }
    }
}

