package com.example.call_scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    TextView tvName1, tvTime1, tvName2, tvTime2;
    Button btnSubmit, btnCheckAvailability;
    Button btnCallTime1, btnCallTime2;
    CallRequest[] calls;
    String timePicker = "";

    SimpleDateFormat sdf;
    Date time1, time2;
    private static final String TAG = "MainActivity2";
    private int interval1, interval2;
    private Handler handler;
    private Runnable runnableFiveMinTo1, runnableFiveMinTo2, runnableCallTo1, runnableCallTo2;


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


        handler = new Handler();
        runnableFiveMinTo1 = new Runnable() {
            public void run() {
                Toast.makeText(MainActivity2.this,  "at most 5 min left to call " + calls[0].getName(), Toast.LENGTH_SHORT).show();
            }
        };
        runnableFiveMinTo2 = new Runnable() {
            public void run() {
                Toast.makeText(MainActivity2.this, "at most 5 min left to call " + calls[1].getName(), Toast.LENGTH_SHORT).show();
            }
        };

        runnableCallTo1 = new Runnable() {
            public void run() {
                startCall(calls[0].getPhone());
            }
        };
        runnableCallTo2 = new Runnable() {
            public void run() {
                startCall(calls[1].getPhone());
            }
        };
    }

    public void startCall(String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + number);
        intent.setData(data);
        if(ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
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
        } else if (v == btnCallTime2) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            timePicker = "call2";
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        } else if (v == btnCheckAvailability) {
            boolean isAvailable = AvailabilityCheck.AvailabilityCheck(calls, time1, time2);
            btnSubmit.setEnabled(isAvailable);
            if (isAvailable)
                Toast.makeText(this, "All contacts available, click submit to continue", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "One or more contacts aren't available, please try again", Toast.LENGTH_LONG).show();
        } else if (v == btnSubmit) {
            Toast.makeText(this, "The app will notify you 5 minutes before any call", Toast.LENGTH_LONG).show();

            // get current time
            long millis = System.currentTimeMillis();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(millis);

            int hours = c.get(Calendar.HOUR_OF_DAY);
            int minutes = c.get(Calendar.MINUTE);

            int hourInterval = (time1.getHours() - hours) % 24;
            int minInterval = (time1.getMinutes() - minutes - 5) % 60;
            // 1000 * 60
            interval1 = (hourInterval * 60 * 1000 * 60) + (minInterval * 60 * 1000);
            handler.postAtTime(runnableFiveMinTo1, System.currentTimeMillis() + interval1);
            handler.postDelayed(runnableFiveMinTo1, interval1);

            hourInterval = (time2.getHours() - hours) % 24;
            minInterval = (time2.getMinutes() - minutes - 5) % 60;
            interval2 = (hourInterval * 60 * 1000 * 60) + (minInterval * 60 * 1000);

            handler.postAtTime(runnableFiveMinTo2, System.currentTimeMillis() + interval2);
            handler.postDelayed(runnableFiveMinTo2, interval2);

            hourInterval = (time1.getHours() - hours) % 24;
            minInterval = (time1.getMinutes() - minutes) % 60;
            interval1 = (hourInterval * 60 * 1000 * 60) + (minInterval * 60 * 1000);
            handler.postAtTime(runnableCallTo1, System.currentTimeMillis() + interval1);
            handler.postDelayed(runnableCallTo1, interval1);

            hourInterval = (time2.getHours() - hours) % 24;
            minInterval = (time2.getMinutes() - minutes) % 60;
            interval2 = (hourInterval * 60 * 1000 * 60) + (minInterval * 60 * 1000);
            handler.postAtTime(runnableCallTo2, System.currentTimeMillis() + interval2);
            handler.postDelayed(runnableCallTo2, interval2);

        }

    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions, int[] grantResults) {}


    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (timePicker == "")
                return;
            if (timePicker == "call1") {
                String str = "Call time for " + calls[0].getName() + ": " + hourOfDay + ":" + minute;
                btnCallTime1.setText(str);
                try {
                    time1 = sdf.parse(hourOfDay + ":" + minute);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (timePicker == "call2") {
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

