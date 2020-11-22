package com.example.call_scheduler;
import java.io.Serializable;


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

    @Override
    public String toString() {
        return "CallRequest{" +
                "startHour=" + startHour +
                ", startMin=" + startMin +
                ", endHour=" + endHour +
                ", endMin=" + endMin +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
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