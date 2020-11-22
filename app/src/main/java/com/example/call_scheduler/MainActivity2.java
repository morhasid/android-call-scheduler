package com.example.call_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    TextView tvName1, tvTime1, tvName2, tvTime2;
    Button btnSubmit;
    EditText etCall1, etCall2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        CallRequest[] calls = (CallRequest[]) intent.getSerializableExtra("calls");

        //HashMap<String, CallRequest> contacts = new Contacts().getContacts();

        tvName1 = findViewById(R.id.tvName1);
        tvName1.setText(calls[0].getName());

        tvTime1 = findViewById(R.id.tvTime1);
        String time = calls[0].getStartHour() + ":" + calls[0].getStartMin() + " - "
                + calls[0].getEndHour() + ":" + calls[0].getEndMin();
        tvTime1.setText(time);

        tvName2 = findViewById(R.id.tvName2);
        tvName2.setText(calls[1].getName());

        tvTime2 = findViewById(R.id.tvTime2);
        time = calls[1].getStartHour() + ":" + calls[1].getStartMin() + " - "
                + calls[1].getEndHour() + ":" + calls[1].getEndMin();
        tvTime2.setText(time);

        etCall1 = findViewById(R.id.etCall1);
        etCall2 = findViewById(R.id.etCall2);
        etCall1.setHint("Hour for " + calls[0].getName());
        etCall2.setHint("Hour for " + calls[1].getName());

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}