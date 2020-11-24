/* Assignment: 1
Campus: Ashdod
Author: Mor Hasid, ID: 204676332
Author2: Limor Shavit, ID: 206227787
*/
package com.example.call_scheduler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    TextView tvName1, tvTime1, tvName2, tvTime2;
    Button btnSubmit, btnCheckAvailability;
    Button btnCallTime1, btnCallTime2;
    String timePicker = "";

    // isPicked contains 2 booleans indicates whether the user
    // picked a time from time picker or not
    Boolean[] isPicked = new Boolean[2];

    // get calls objects array from intent
    CallRequest[] calls;

    // Date & SimpleDateFormat objects help to compare times conveniently
    Date time1, time2;
    SimpleDateFormat sdf;
    // time for the system to wait for make calls and show toasts (in milliseconds)
    private int interval1, interval2;
    // Handler helps us to run the runnable objects at X time from now
    private Handler handler;
    // Tasks to do in the future -> make calls and show toasts
    private Runnable runnableFiveMinTo1, runnableFiveMinTo2, runnableCallTo1, runnableCallTo2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Get the calls array from MainActivity
        Intent intent = getIntent();
        calls = (CallRequest[]) intent.getSerializableExtra("calls");

        // Set the text TextViews & Buttons according to data from calls (from MainActivity)
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

        // This Button checks whether all contact are available on chosen time
        btnCheckAvailability = findViewById(R.id.btnCheckAvailability);
        btnCheckAvailability.setOnClickListener(this);

        // Submit is enable only if the user pressed btnCheckAvailability succeed
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        btnSubmit.setEnabled(false);

        // pattern for time (in Date object)
        String pattern = "HH:mm";
        sdf = new SimpleDateFormat(pattern);

        // User didn't pick time from time picker yet
        Arrays.fill(isPicked, Boolean.FALSE);

        // Handler helps us to run the runnable objects at X time from now
        // Create runnable for every future task
        handler = new Handler();
        // Toast alert 5 minute before call to contacts
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
        // make call to contacts
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

    // make call after check mobile permission
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
        // pick time range for each contact call request
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
            // if the user didn't pick time for all contacts -> toast alert
            if(!InputValidation.isTimeValid(isPicked)) {
                Toast.makeText(this,  "One of the time ranges is not picked" , Toast.LENGTH_SHORT).show();
                return;
            }
            // checks if all calls are in time range availability of the contacts.
            // submit button is enabled according availability check.
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

            // calculates the time to wait until the first call toast (5 min before)
            int hourInterval = (time1.getHours() - hours) % 24;
            int minInterval = (time1.getMinutes() - minutes - 5) % 60;
            interval1 = (hourInterval * 60 * 1000 * 60) + (minInterval * 60 * 1000);
            // Set the task, and time to run it
            handler.postAtTime(runnableFiveMinTo1, System.currentTimeMillis() + interval1);
            handler.postDelayed(runnableFiveMinTo1, interval1);

            // calculates the time to wait until the second call toast (5 min before)
            hourInterval = (time2.getHours() - hours) % 24;
            minInterval = (time2.getMinutes() - minutes - 5) % 60;
            interval2 = (hourInterval * 60 * 1000 * 60) + (minInterval * 60 * 1000);
            // Set the task, and time to run it
            handler.postAtTime(runnableFiveMinTo2, System.currentTimeMillis() + interval2);
            handler.postDelayed(runnableFiveMinTo2, interval2);

            // calculates the time to wait to make the first call
            hourInterval = (time1.getHours() - hours) % 24;
            minInterval = (time1.getMinutes() - minutes) % 60;
            interval1 = (hourInterval * 60 * 1000 * 60) + (minInterval * 60 * 1000);
            handler.postAtTime(runnableCallTo1, System.currentTimeMillis() + interval1);
            handler.postDelayed(runnableCallTo1, interval1);

            // calculates the time to wait to make the second call
            hourInterval = (time2.getHours() - hours) % 24;
            minInterval = (time2.getMinutes() - minutes) % 60;
            interval2 = (hourInterval * 60 * 1000 * 60) + (minInterval * 60 * 1000);
            handler.postAtTime(runnableCallTo2, System.currentTimeMillis() + interval2);
            handler.postDelayed(runnableCallTo2, interval2);
        }
    }

    // Open a time picker for call time requests
    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (timePicker == "")
                return;
            if (timePicker == "call1") {
                isPicked[0] = true;
                String str = "Call time for " + calls[0].getName() + ": " + hourOfDay + ":" + minute;
                btnCallTime1.setText(str);
                try {
                    time1 = sdf.parse(hourOfDay + ":" + minute);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (timePicker == "call2") {
                isPicked[1] = true;
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

