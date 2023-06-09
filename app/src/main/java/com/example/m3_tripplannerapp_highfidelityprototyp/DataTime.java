package com.example.m3_tripplannerapp_highfidelityprototyp;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * structures a date due to format: hour(s):minute(s)
 * @author TripPlannerApp-Team
 */
public class DataTime implements Comparable<DataTime>, Serializable {

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
     * compares Time by first hour, than minute
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(DataTime other) {
        if (this.hour < other.hour) {
            return -1;
        } else if (this.hour > other.hour) {
            return 1;
        } else {
            if (this.minute < other.minute) {
                return -1;
            } else if (this.minute > other.minute) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * compares time by using compareTo
     * @param thatTime
     * @return
     */
    public DataEnumTimeComparison compareThisTimeToThatTime(DataTime thatTime){
        if(this == null) return DataEnumTimeComparison.Later;
        if(thatTime == null) return DataEnumTimeComparison.Earlier;
        if (this.compareTo(thatTime) < 0) {
            System.out.println(this + " is earlier than " + thatTime);
            return DataEnumTimeComparison.Earlier;
        } else if (this.compareTo(thatTime) > 0) {
            return DataEnumTimeComparison.Later;
        } else {
            return DataEnumTimeComparison.Equal;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataTime dataTime = (DataTime) o;
        return minute == dataTime.minute && hour == dataTime.hour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minute, hour);
    }

    /**
     * a representation as String for testing and viewing purpose
     * @return String (not null)
     */
    @NonNull
    @Override
    public String toString() {
        String minuteString = "";
        if(minute < 10){
            minuteString = "0" + minute;
        } else {
            minuteString = minute + "";
        }
        String hourString = "";
        if(hour < 10){
            hourString = "0" + hour;
        } else {
            hourString = hour + "";
        }
        return " "+hourString+":"+minuteString+" Uhr";
    }



}
