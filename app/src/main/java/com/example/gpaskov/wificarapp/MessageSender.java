package com.example.gpaskov.wificarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by gpaskov on 01-04-16.
 */

public class MessageSender extends AppCompatActivity {

    EditText textSend;
    TextView error, textReceive;
    Button buttonSend, buttonReceive;
    String message;
    MessageSender activity;
    private String response = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String runningMode = intent.getStringExtra(MainActivity.EXTRA_RUNNING_MODE);

        if (runningMode != null) {
            createLayout(runningMode);
        }
    }

    public void createLayout(String runningMode) {
        setContentView(R.layout.activity_send_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textReceive = (TextView) findViewById(R.id.textReceive);
        textReceive.setVisibility(View.INVISIBLE);

        textSend = (EditText) findViewById(R.id.textSend);
        textSend.setVisibility(View.INVISIBLE);

        buttonSend = (Button) findViewById(R.id.button_send);
        buttonSend.setVisibility(View.INVISIBLE);

        buttonReceive = (Button) findViewById(R.id.buttonReceive);
        buttonReceive.setVisibility(View.INVISIBLE);

        error = (TextView) findViewById(R.id.textView);
        error.setVisibility(View.INVISIBLE);

        final Socket socket = SocketHandler.getSocket();

        if (socket != null) {

            if (runningMode.equals(MainActivity.RUN_MODE)) {

                Log.d("socketHandler is null", "Exit!");

            } else {
                textReceive.setVisibility(View.VISIBLE);
                textSend.setVisibility(View.VISIBLE);
                buttonSend.setVisibility(View.VISIBLE);
                buttonReceive.setVisibility(View.VISIBLE);


                /**   while (!Thread.currentThread().isInterrupted()) {

                 try {
                 BufferedReader in = new BufferedReader(
                 new InputStreamReader(socket.getInputStream()));
                 String inputText = in.readLine();
                 textReceive.setText(inputText);

                 } catch (IOException e) {
                 e.printStackTrace();
                 }
                 } */

                buttonReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        InputStream inputStream = null;
                        try {

                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                            byte[] buffer = new byte[1024];

                            int bytesRead;
                            inputStream = socket.getInputStream();

                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                byteArrayOutputStream.write(buffer, 0, bytesRead);
                                response += byteArrayOutputStream.toString("UTF-8");
                            }

                            textReceive.setText(response);

                        } catch (IOException e) {
                            e.printStackTrace();
                            message += "Something is wrong with receiving data! " + e.toString() + "\n";

                            MessageSender.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.error.setText(message);
                                }
                            });
                        }
                    }
                });

                buttonSend.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        PrintWriter out = null;
                        try {

                            out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream())), true);

                            out.print(textSend.getText().toString().trim());
//                            out.flush();

                        } catch (UnknownHostException e) {
                            e.printStackTrace();

                            message += "Something went wrong with sending data! " + e.toString() + "\n";

                            MessageSender.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.error.setText(message);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                            message += "Something went wrong with sending data! " + e.toString() + "\n";

                            MessageSender.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.error.setText(message);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            message += "Something went wrong with sending data! " + e.toString() + "\n";

                            MessageSender.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.error.setText(message);
                                }
                            });
                        } finally {
                            out.flush();
                        }
                    }
                });
            }
        } else {

            Log.d("socketHandler is null", "Exit!");
            error = (TextView) findViewById(R.id.textView);
            error.setVisibility(View.VISIBLE);
        }
    }


}

