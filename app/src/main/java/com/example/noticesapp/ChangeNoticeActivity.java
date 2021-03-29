package com.example.noticesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeNoticeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button applyButton;
    private EditText textGetter;
    private boolean isDeleteNotice = false;
    Intent data = new Intent();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_notice_activity);
        applyButton = findViewById(R.id.applyButton);
        textGetter = findViewById(R.id.noticeFieldForChange);
        applyButton.setOnClickListener(this);
        if (getIntent().getStringExtra("text") != null) {
            String noticetext = getIntent().getStringExtra("text");
            textGetter.setText(noticetext);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_function:
                isDeleteNotice = true;
                data.putExtra("flag", isDeleteNotice);
                setResult(RESULT_OK, data);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_notice_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.applyButton:
                data.putExtra("changedNote", textGetter.getText().toString());
                data.putExtra("flag", isDeleteNotice);
                setResult(RESULT_OK, data);
                finish();

        }
    }
}


