package com.example.call_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    TextView tvName1, tvTime1, tvName2, tvTime2;
    Button btnSubmit, btnCheckAvailability;
    Button btnCallTime1, btnCallTime2;
    CallRequest[] calls;
    String timePicker = "";

    SimpleDateFormat sdf;
    Date time1, time2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String pattern = "HH:mm";
        sdf = new SimpleDateFormat(pattern);

        Intent intent = getIntent();
        calls = (CallRequest[]) intent.getSerializableExtra("calls");

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

        btnCallTime1 = findViewById(R.id.btnCallTime1);
        btnCallTime1.setOnClickListener(this);
        btnCallTime1.setText("Select time for " + calls[0].getName());

        btnCallTime2 = findViewById(R.id.btnCallTime2);
        btnCallTime2.setOnClickListener(this);
        btnCallTime2.setText("Select time for " + calls[1].getName());

        btnCheckAvailability = findViewById(R.id.btnCheckAvailability);
        btnCheckAvailability.setOnClickListener(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        btnSubmit.setEnabled(false);
    }

    @Override
    public void onClick(View v) {

        if (v == btnCallTime1) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            timePicker = "call1";
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        }
        if (v == btnCallTime2) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            timePicker = "call2";
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        }
        if(v == btnCheckAvailability) {
            boolean isOk = AvailabilityCheck.AvailabilityCheck(calls, time1, time2);
            btnSubmit.setEnabled(isOk);
        }
        if(v == btnSubmit) {
            //Toast.makeText(this, isOk ? "YESS" : "NO", Toast.LENGTH_LONG).show();
        }
    }


    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(timePicker == "")
                return;
            if(timePicker == "call1") {
                String str = "Call time for " + calls[0].getName() + ": " + hourOfDay + ":" + minute;
                btnCallTime1.setText(str);
                try {
                    time1 = sdf.parse(hourOfDay + ":" + minute);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(timePicker == "call2") {
                String str = "Call time for " + calls[1].getName() + ": " + hourOfDay + ":" + minute;
                btnCallTime2.setText(str);
                try {
                    time2 = sdf.parse(hourOfDay + ":" + minute);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            timePicker = "";
        }
    }
}