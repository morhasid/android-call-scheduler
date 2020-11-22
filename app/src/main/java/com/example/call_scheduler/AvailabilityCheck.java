package com.example.call_scheduler;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class AvailabilityCheck {

    public static boolean AvailabilityCheck(CallRequest[] calls, Date time1, Date time2) {
        Date start1 = null, end1=null, start2=null, end2=null;
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            start1 = sdf.parse(calls[0].getStartHour() + ":" + calls[0].getStartMin());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            end1 = sdf.parse(calls[0].getEndHour() + ":" + calls[0].getEndMin());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            start2 = sdf.parse(calls[1].getStartHour() + ":" + calls[1].getStartMin());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            end2 = sdf.parse(calls[1].getEndHour() + ":" + calls[1].getEndMin());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // check if   starti <= timei <= endi
        // Outputs -1 as date1 is before date2
//        System.out.println(date1.compareTo(date2));

        // bad
        boolean isAvailable = true;
        if(start1.compareTo(time1) == 1  || end1.compareTo(time1) == -1) {
            isAvailable = false;
        }

        if(start2.compareTo(time2) == 1  || end2.compareTo(time2) == -1) {
            isAvailable = false;
        }

        return isAvailable;
    }
}
