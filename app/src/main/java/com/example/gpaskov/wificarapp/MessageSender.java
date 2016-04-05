package com.example.gpaskov.wificarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by gpaskov on 01-04-16.
 */

public class MessageSender extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String runningMode = intent.getStringExtra(MainActivity.EXTRA_RUNNING_MODE);

        if (runningMode != null) {
            if (runningMode.equals(MainActivity.RUN_MODE)) {

                setContentView(R.layout.activity_send_message);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                TextView textView = new TextView(this);
                textView.setTextSize(40);
                textView.setText(runningMode);

                RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
                layout.addView(textView);

            } else {

                setContentView(R.layout.activity_send_message);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                TextView textView = new TextView(this);
                textView.setTextSize(40);
                textView.setText(runningMode);

                RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
                layout.addView(textView);

            }
        }

    }

}
