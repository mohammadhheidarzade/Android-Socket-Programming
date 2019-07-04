package com.mesider.client;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public static Object input;
    public static Object output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MyTask().execute();

        Button send = findViewById(R.id.send);
        final EditText username = findViewById(R.id.username);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output = username.getText().toString();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(input);
                if (input.equals(false))
                    Toast.makeText(MainActivity.this , "false" , Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this , "true" , Toast.LENGTH_LONG).show();
            }
        });
    }
}
class MyTask extends AsyncTask<String, Void, Void> {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    @Override
    protected Void doInBackground(String... strings) {
        try {
            socket = new Socket("192.168.1.107", 8888);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {//for send information
            @Override
            public void run() {
                while (true) {
                    if (MainActivity.output != null) {
                        try {
                            output.writeObject(MainActivity.output);
                            output.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MainActivity.output = null;
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {//for send information
            @Override
            public void run() {
                while (true) {
                    try {
                        MainActivity.input = input.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return null;
    }
}
