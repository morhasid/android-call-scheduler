/* Assignment: 1
Campus: Ashdod
Author: Mor Hasid, ID: 204676332
Author2: Limor Shavit, ID: 206227787
*/
package com.example.call_scheduler;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 Class Defines the MainActivity
 */
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnSelectStartTime1, btnSelectEndTime1;
    Button btnSubmit;
    EditText etPhone1, etName1;
    Button btnSelectStartTime2, btnSelectEndTime2;
    EditText etPhone2, etName2;

    SharedPreferences pref;

    // CallRequest object contains all necessary data about availability of the phone call
    // in order to forward it to MainActivity2
    CallRequest call1, call2;

    // isPicked contains 4 booleans indicates whether the user
    // picked a time from time picker or not
    Boolean[] isPicked = new Boolean[4];

    // use this string to identify in the function onTimeSet which time picker we clicked
    String timePicker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init SP object
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        call1 = new CallRequest();
        call2 = new CallRequest();

        etName1 = findViewById(R.id.etName1);
        // get the name from SP object, if not available returns null
        etName1.setText(pref.getString("name1", ""));

        etPhone1 = findViewById(R.id.edPhone1);
        etPhone1.setText(pref.getString("phone1", ""));

        btnSelectStartTime1 = (Button) findViewById(R.id.btnStartTime1);
        btnSelectStartTime1.setOnClickListener(this);

        btnSelectEndTime1 = (Button) findViewById(R.id.btnEndTime1);
        btnSelectEndTime1.setOnClickListener(this);

        etName2 = findViewById(R.id.etName2);
        etName2.setText(pref.getString("name2", ""));

        etPhone2 = findViewById(R.id.edPhone2);
        etPhone2.setText(pref.getString("phone2", ""));


        btnSelectStartTime2 = (Button) findViewById(R.id.btnStartTime2);
        btnSelectStartTime2.setOnClickListener(this);

        btnSelectEndTime2 = (Button) findViewById(R.id.btnEndTime2);
        btnSelectEndTime2.setOnClickListener(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        // User didn't pick time from time picker yet
        Arrays.fill(isPicked, Boolean.FALSE);
    }

    // Function gets a call and return start/end (depends on the boolean) Date object
    // for convenient time comparison
    public Date getDateObject(CallRequest call, boolean isStart) {
        Date date = null;
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        // make Date objects
        try {
            date = isStart ? sdf.parse(call.getStartHour() + ":" + call.getStartMin()) :
                    sdf.parse(call.getEndHour() + ":" + call.getEndMin());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public void onClick(View v) {
        if (v == btnSelectStartTime1) {
            // Open a time picker
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
            // get the data from user
            String name1 = etName1.getText().toString(), name2= etName2.getText().toString();
            String phone1 = etPhone1.getText().toString(), phone2 = etPhone2.getText().toString();

            // Input Validation
            if(!InputValidation.isNameValid(name1)) {
                Toast.makeText(this,  "First name " + name1 +" is not valid" , Toast.LENGTH_SHORT).show();
                return;
            }
            if(!InputValidation.isNameValid(name2)) {
                Toast.makeText(this,  "Second name " + name2 +" is not valid" , Toast.LENGTH_SHORT).show();
                return;
            }
            if(!InputValidation.isPhoneValid(phone1)) {
                Toast.makeText(this,  "First phone " + phone1 +" is not valid, try 05........" , Toast.LENGTH_SHORT).show();
                return;
            }
            if(!InputValidation.isPhoneValid(phone2)) {
                Toast.makeText(this,  "Second phone " + phone2 +" is not valid, try 05........" , Toast.LENGTH_SHORT).show();
                return;
            }
            if(!InputValidation.isTimeValid(isPicked)) {
                Toast.makeText(this,  "One of the time ranges is not picked" , Toast.LENGTH_SHORT).show();
                return;
            }
            // if start time is before end time
            if(!InputValidation.isRangesValid(getDateObject(call1, true), getDateObject(call1, false)) ||
                    !InputValidation.isRangesValid(getDateObject(call2, true), getDateObject(call2, false))) {
                Toast.makeText(this,  "Start time is not before end time" , Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("name1", name1);
            editor.putString("name2", name2);
            editor.putString("phone1", phone1);
            editor.putString("phone2", phone2);
            editor.commit(); // commit changes sharedPreference

            call1.setName(name1);
            call1.setPhone(phone1);
            call2.setName(name2);
            call2.setPhone(phone2);

            // forward calls data to MainActivity2
            Intent i = new Intent(MainActivity.this, MainActivity2.class);
            CallRequest[] calls = new CallRequest[2];
            calls[0] = call1;
            calls[1] = call2;
            i.putExtra("calls", calls);
            startActivity(i);
        }
    }

    /**
     * this class Manages TimePicker
     * Opens a time picker for call time ranges
     */
    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(timePicker == "")
                return;
            if(timePicker == "start1") {
                isPicked[0] = true;
                String str = "Start time: " + hourOfDay + ":" + minute;
                btnSelectStartTime1.setText(str);
                call1.setStartHour(hourOfDay);
                call1.setStartMin(minute);
            }
            if(timePicker == "end1") {
                isPicked[1] = true;
                String str = "End time: " + hourOfDay + ":" + minute;
                btnSelectEndTime1.setText(str);
                call1.setEndHour(hourOfDay);
                call1.setEndMin(minute);
            }
            if(timePicker == "start2") {
                isPicked[2] = true;
                String str = "Start time: " + hourOfDay + ":" + minute;
                btnSelectStartTime2.setText(str);
                call2.setStartHour(hourOfDay);
                call2.setStartMin(minute);
            }
            if(timePicker == "end2") {
                isPicked[3] = true;
                String str = "End time: " + hourOfDay + ":" + minute;
                btnSelectEndTime2.setText(str);
                call2.setEndHour(hourOfDay);
                call2.setEndMin(minute);
            }
            timePicker = "";
        }
    }



}


