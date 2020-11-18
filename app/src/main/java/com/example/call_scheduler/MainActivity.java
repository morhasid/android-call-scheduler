package com.example.call_scheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnSelectStartTime, btnSelectEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelectStartTime = (Button) findViewById(R.id.btnStartTime);
        btnSelectStartTime.setOnClickListener(this);

        btnSelectEndTime = (Button) findViewById(R.id.btnEndTime);
        btnSelectEndTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectStartTime) {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = systemCalendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new SetYourTime(),
                    hour, minute, true);
            timePickerDialog.show();
        }
    }

    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String str = "Time is :" + hourOfDay + ":" + minute;
            btnSelectStartTime.setText(str);
        }

    }

}


