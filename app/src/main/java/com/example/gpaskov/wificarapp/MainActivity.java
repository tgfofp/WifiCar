package com.example.gpaskov.wificarapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by gpaskov on 25-03-16.
 */


public class MainActivity extends Activity {

    private TextView response;
    private EditText editTextAddress, editTextPort;
    private Button buttonConnect, buttonClear, buttonDebug, buttonRun;

    public final static String EXTRA_RUNNING_MODE = "com.example.gpaskov.wificarapp.RUNNING_MODE";
    public final static String RUN_MODE = "run";
    public final static String DEBUG_MODE = "debug";

    private String runningMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.addressEditText);
        editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        response = (TextView) findViewById(R.id.responseTextView);
        buttonDebug = (Button) findViewById(R.id.debug_button);
        buttonRun = (Button) findViewById(R.id.run_button);


        buttonConnect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Client myClient = new Client(editTextAddress.getText()
                        .toString().trim(), Integer.parseInt(editTextPort
                        .getText().toString().trim()), response);
                myClient.execute();

                buttonDebug.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runningMode = DEBUG_MODE;
                        Intent intent = new Intent(MainActivity.this, MessageSender.class);
                        intent.putExtra(EXTRA_RUNNING_MODE, runningMode);
                        startActivity(intent);
                    }
                });

                buttonRun.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runningMode = RUN_MODE;
                        Intent intent = new Intent(MainActivity.this, MessageSender.class);
                        intent.putExtra(EXTRA_RUNNING_MODE, runningMode);
                        startActivity(intent);
                    }
                });
            }
        });

        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });
    }

}