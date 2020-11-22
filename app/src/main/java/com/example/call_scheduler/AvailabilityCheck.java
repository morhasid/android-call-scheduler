package com.example.call_scheduler;

import java.util.HashMap;
import java.time.LocalDateTime;

public class AvailabilityCheck {
    private static HashMap<String, CallRequest> contacts;

    public AvailabilityCheck(HashMap<String, CallRequest> allContacts) {
        contacts = allContacts;
    }


    public static boolean checkAvailability(CallRequest call) {
        if(!contacts.containsKey(call.getName()))
            return false;

//        LocalDateTime callStartDate = LocalDateTime.of(call.getYear(), call.getMonth(), call.getDay(),
//                call.getStartHour(), call.getStartMin(), 0);
//        LocalDateTime callEndDate = LocalDateTime.of(call.getYear(), call.getMonth(), call.getDay(),
//                call.getEndHour(), call.getEndMin(), 0);




        return true;
    }
}
