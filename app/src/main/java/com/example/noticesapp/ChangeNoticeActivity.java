package com.example.noticesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeNoticeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button delButton,applyButton;
    private EditText textGetter;
    private String noticetext;
    private boolean isDeleteNotice = false;
    Intent data = new Intent();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_notice_activity);
        delButton = findViewById(R.id.deleteButton);
        applyButton = findViewById(R.id.applyButton);
        textGetter = findViewById(R.id.noticeFieldForChange);
        delButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);
        if (getIntent().getStringExtra("text") != null) {
            noticetext = getIntent().getStringExtra("text");
            textGetter.setText(noticetext);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.applyButton:
                data.putExtra("changedNote",textGetter.getText().toString());
                data.putExtra("flag",isDeleteNotice);
                setResult(RESULT_OK,data);
                finish();
            case R.id.deleteButton:
                isDeleteNotice = true;
                data.putExtra("flag",isDeleteNotice);
                setResult(RESULT_OK,data);
                finish();
        }
    }
}
