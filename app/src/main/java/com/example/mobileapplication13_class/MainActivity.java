package com.example.mobileapplication13_class;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    String SERVER_IP = "172.17.99.207";
    int SERVER_PORT = 200;
    String msg = "";
    EditText e1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        if(view.getId() == R.id.b2){
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.b3){
            Intent intent = new Intent(MainActivity.this, Main3Activity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.b4){
            Intent intent = new Intent(MainActivity.this, Main4Activity.class);
            startActivity(intent);
        }
    }

}
