package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.annotation.NonNull;

/**
 * structures a date due to format: hour(s):minute(s)
 * @author TripPlannerApp-Team
 */
public class DataTime {

    private int minute;
    private int hour;


    public DataTime(int minute, int hour) {
        this.minute = minute;
        this.hour = hour;
    }


    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    /**
     * a representation as String for testing and viewing purpose
     * @return String (not null)
     */
    @NonNull
    @Override
    public String toString() {
        return "["+hour+":"+minute+"]";
    }
}
