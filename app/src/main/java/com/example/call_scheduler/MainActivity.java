package com.example.call_scheduler;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnSelectStartTime1, btnSelectEndTime1;
    Button btnSubmit;
    String timePicker = "";
    EditText etPhone1, etName1;
    CallRequest call1, call2;

    Button btnSelectStartTime2, btnSelectEndTime2;
    EditText etPhone2, etName2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        call1 = new CallRequest();
        call2 = new CallRequest();

        etName1 = findViewById(R.id.etName1);
        etPhone1 = findViewById(R.id.edPhone1);

        btnSelectStartTime1 = (Button) findViewById(R.id.btnStartTime1);
        btnSelectStartTime1.setOnClickListener(this);

        btnSelectEndTime1 = (Button) findViewById(R.id.btnEndTime1);
        btnSelectEndTime1.setOnClickListener(this);
        //
        etName2 = findViewById(R.id.etName2);
        etPhone2 = findViewById(R.id.edPhone2);

        btnSelectStartTime2 = (Button) findViewById(R.id.btnStartTime2);
        btnSelectStartTime2.setOnClickListener(this);

        btnSelectEndTime2 = (Button) findViewById(R.id.btnEndTime2);
        btnSelectEndTime2.setOnClickListener(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectStartTime1) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            timePicker = "start1";
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        }
        if (v == btnSelectEndTime1) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            timePicker = "end1";
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        }
        if (v == btnSelectStartTime2) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            timePicker = "start2";
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        }
        if (v == btnSelectEndTime2) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            timePicker = "end2";
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        }

        if(v == btnSubmit) {
            call1.setName(etName1.getText().toString());
            call1.setPhone(etPhone1.getText().toString());
            call2.setName(etName2.getText().toString());
            call2.setPhone(etPhone2.getText().toString());
            Intent i = new Intent(MainActivity.this, MainActivity2.class);
            CallRequest[] calls = new CallRequest[2];
            calls[0] = call1;
            calls[1] = call2;
            i.putExtra("calls", calls);
            startActivity(i);
        }
    }

    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(timePicker == "")
                return;
            if(timePicker == "start1") {
                String str = "Start time: " + hourOfDay + ":" + minute;
                btnSelectStartTime1.setText(str);
                call1.setStartHour(hourOfDay);
                call1.setStartMin(minute);
            }
            if(timePicker == "end1") {
                String str = "End time: " + hourOfDay + ":" + minute;
                btnSelectEndTime1.setText(str);
                call1.setEndHour(hourOfDay);
                call1.setEndMin(minute);
            }
            if(timePicker == "start2") {
                String str = "Start time: " + hourOfDay + ":" + minute;
                btnSelectStartTime2.setText(str);
                call2.setStartHour(hourOfDay);
                call2.setStartMin(minute);
            }
            if(timePicker == "end2") {
                String str = "End time: " + hourOfDay + ":" + minute;
                btnSelectEndTime2.setText(str);
                call2.setEndHour(hourOfDay);
                call2.setEndMin(minute);
            }
            timePicker = "";
        }
    }



}


