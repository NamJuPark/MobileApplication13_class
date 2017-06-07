package com.example.mobileapplication13_class;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main2Activity extends AppCompatActivity {
    TextView view;
    EditText etURL;
    String urlstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etURL = (EditText)findViewById(R.id.editText);
        view = (TextView)findViewById(R.id.editText2);
    }
    Handler mHandler = new Handler();
    Thread mThread = new Thread(){
        @Override
        public void run() {
            try {
                URL url = new URL(urlstr);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    final String data = readData(urlConnection.getInputStream());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setText(data);
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private String readData(InputStream inputStream) {
        String data = "";
        Scanner s = new Scanner(inputStream);
        while(s.hasNext()) data += s.nextLine() + "\n";
        s.close();
        return data;
    }

    public void onClick(View view){
        if(view.getId() == R.id.bDownload){
            urlstr = etURL.getText().toString();
            mThread.start();
        }
    }
}
