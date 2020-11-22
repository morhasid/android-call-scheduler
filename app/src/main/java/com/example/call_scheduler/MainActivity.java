package com.example.call_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;


import java.util.Calendar;



public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnSelectStartTime, btnSelectEndTime, btnSelectDate;
    Button btnSubmit;
    String timePicker = "";
    EditText etPhone, etName;
    CallRequest call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        call = new CallRequest();
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.edPhone);

        btnSelectDate = findViewById(R.id.btnDate);
        btnSelectDate.setOnClickListener(this);

        btnSelectStartTime = (Button) findViewById(R.id.btnStartTime);
        btnSelectStartTime.setOnClickListener(this);

        btnSelectEndTime = (Button) findViewById(R.id.btnEndTime);
        btnSelectEndTime.setOnClickListener(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectStartTime) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            timePicker = "start";
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        }
        if (v == btnSelectEndTime) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            timePicker = "end";
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        }
        if(v == btnSelectDate) {
            Calendar systemCalendar = Calendar.getInstance();
            int year=systemCalendar.get(Calendar.YEAR);
            int month=systemCalendar.get(Calendar.MONTH);
            int day=systemCalendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(this,new SetDate(),year,month,day);
            datePickerDialog.show();
        }
        if(v == btnSubmit) {
            call.setName(etName.getText().toString());
            call.setPhone(etPhone.getText().toString());
            Intent i = new Intent(MainActivity.this, MainActivity2.class);
            i.putExtra("call", call);
            startActivity(i);
        }
    }

    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(timePicker == "")
                return;
            if(timePicker == "start") {
                String str = "Start time: " + hourOfDay + ":" + minute;
                btnSelectStartTime.setText(str);
                call.setStartHour(hourOfDay);
                call.setStartMin(minute);
            }
            if(timePicker == "end") {
                String str = "End time: " + hourOfDay + ":" + minute;
                btnSelectEndTime.setText(str);
                call.setEndHour(hourOfDay);
                call.setEndMin(minute);
            }
            timePicker = "";
        }
    }

    final class SetDate implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            String str = "You selected: "+ dayOfMonth + "/" + monthOfYear + "/"+year;
            btnSelectDate.setText(str);
            call.setYear(year);
            call.setDay(dayOfMonth);
            call.setMonth(monthOfYear);
        }
    }


}


