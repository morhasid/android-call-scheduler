/* Assignment: 1
Campus: Ashdod
Author: Mor Hasid, ID: 204676332
Author2: Limor Shavit, ID: 206227787
*/
package com.example.call_scheduler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class checks for each call request time if the contact is available
 */
public class AvailabilityCheck {

    public static boolean AvailabilityCheck(CallRequest[] calls, Date time1, Date time2) {
        // Date & SimpleDateFormat objects help to compare times conveniently
        Date start1 = null, end1=null, start2=null, end2=null;
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        // make Date objects
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


        boolean isAvailable = true;
        // bad scenarios - if the call time not in contact range availability
        if(start1.compareTo(time1) == 1  || end1.compareTo(time1) == -1) {
            isAvailable = false;
        }
        if(start2.compareTo(time2) == 1  || end2.compareTo(time2) == -1) {
            isAvailable = false;
        }

        return isAvailable;
    }
}
