package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.annotation.NonNull;

/**
 * structures a date due to format: day-month-year
 * @author TripPlannerApp-Team
 */
public class DataDate implements Comparable<DataDate> {

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


    @Override
    public int compareTo(DataDate other) {
        if (this.year < other.year) {
            return -1;
        } else if (this.year > other.year) {
            return 1;
        } else {
            if (this.month < other.month) {
                return -1;
            } else if (this.month > other.month) {
                return 1;
            } else {
                if (this.day < other.day) {
                    return -1;
                } else if (this.day > other.day) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public DataEnumTimeComparison compareThisDateToThatDate(DataDate thatDate){
        if(this == null) return DataEnumTimeComparison.Later;
        if(thatDate == null) return DataEnumTimeComparison.Earlier;
        if (this.compareTo(thatDate) < 0) {
            return DataEnumTimeComparison.Earlier;
        } else if (this.compareTo(thatDate) > 0) {
            return DataEnumTimeComparison.Later;
        } else {
            return DataEnumTimeComparison.Equal;
        }
    }


    /**
     * a representation as String for testing and viewing purpose
     * @return String (not null)
     */
    @NonNull
    @Override
    public String toString() {
        String monthString = "";
        if(month < 10){
            monthString = "0"+month;
        } else {
            monthString = month + "";
        }
        return ""+day+"."+monthString+"."+year+"";
    }
}
