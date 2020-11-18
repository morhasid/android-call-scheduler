package com.example.call_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        CallRequest call = (CallRequest) intent.getSerializableExtra("call");

        HashMap<String, CallRequest> contacts = new Contacts().getContacts();
        //Toast.makeText(this, contacts.get("limor").getPhone(), Toast.LENGTH_LONG).show();

    }
}