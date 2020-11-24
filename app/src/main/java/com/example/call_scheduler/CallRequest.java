/* Assignment: 1
Campus: Ashdod
Author: Mor Hasid, ID: 204676332
Author2: Limor Shavit, ID: 206227787
*/
package com.example.call_scheduler;
import java.io.Serializable;

/**
 * Class contains all data for contacts
 * implements Serializable to be able for pass it to the next activity via Intent
 * the function putExtra gets Serializable in the 2nd argument.
 */
final public class CallRequest implements Serializable {
    private int startHour, startMin, endHour, endMin;
    private String name, phone;

    public CallRequest() {
    }

    public CallRequest(CallRequest other) {
        this.startHour = other.startHour;
        this.startMin = other.startMin;
        this.endHour = other.endHour;
        this.endMin = other.endMin;
        this.phone = other.phone;
        this.name = other.name;
    }

    public CallRequest(int startHour, int startMin, int endHour, int endMin,
                       int day, int month, int year, String phone) {
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.phone = phone;
    }


    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}