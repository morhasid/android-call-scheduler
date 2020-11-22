package com.example.call_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    TextView tvName, tvDate, tvTime;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        CallRequest call = (CallRequest) intent.getSerializableExtra("call");

        HashMap<String, CallRequest> contacts = new Contacts().getContacts();

        tvName = findViewById(R.id.tvName);
        tvName.setText(call.getName());

        tvDate = findViewById(R.id.tvDate);
        StringBuilder str = new StringBuilder("");
        str.append(call.getDay()); str.append("/");
        str.append(call.getMonth()); str.append("/");
        str.append(call.getYear());
        tvDate.setText(str);

        tvTime = findViewById(R.id.tvTime);
        String time = call.getStartHour() + ":" + call.getStartMin() + " - "
                + call.getEndHour() + ":" + call.getEndMin();
        tvTime.setText(time);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}