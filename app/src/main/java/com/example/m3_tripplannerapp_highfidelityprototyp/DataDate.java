package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.annotation.NonNull;

/**
 * structures a date due to format: day-month-year
 * @author TripPlannerApp-Team
 */
public class DataDate {

    private int day;
    private int month;
    private int year;


    public DataDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    /**
     * a representation as String for testing and viewing purpose
     * @return String (not null)
     */
    @NonNull
    @Override
    public String toString() {
        return "["+day+"-"+month+"-"+year+"]";
    }
}
