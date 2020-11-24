/* Assignment: 1
Campus: Ashdod
Author: Mor Hasid, ID: 204676332
Author2: Limor Shavit, ID: 206227787
*/
package com.example.call_scheduler;

/**
 * Class check user input
 */
public class InputValidation {
    // name not empty, only english characters
    public static boolean isNameValid(String name) {
        return name.matches("[a-zA-Z]+");
    }

    // user picked all time pickers
    public static boolean isTimeValid(Boolean [] isPicked) {
        for (Boolean pick : isPicked) {
            if (!pick) {
                return false;
            }
        }
        return true;
    }

    // phone only numbers, start with "05", and length of 10
    public static boolean isPhoneValid(String phone) {
        return (phone.length() == 10 && phone.matches("05[0-9]*"));
    }
}
