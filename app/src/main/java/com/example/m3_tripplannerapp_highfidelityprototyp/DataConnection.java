package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * main data communication class
 * @author TripPlannerApp-Team
 */
public class DataConnection implements Serializable {

    // essential data
    private String startCity;
    private String startLocation;
    private String destinationCity; // when "back and forth"; is the city from where the user wants to return from
    private String destinationLocation; // when "back and forth"; is the citys location from where the user wants to return from

    // additional data
    private DataDate startDate;
    private DataTime startTime; // is the time, when user wants to travel back when "back and forth"
    private DataDate returnDate;
    private DataTime returnTime;

    private boolean isReturn;

    // has to be set additionally, not via constructor
    private List<DataConnection> intermediatStations; // all additional stopps between start and destination

    // meta data
    private String company; // name of main vendor
    private int companyLogo;
    private DataTime duration; // in minutes
    private double prize; // in Euro
    private DataEnumTransport type;
    private int switchTransfer; // counts how many
    private double CO2Bilance; // in
    private double distance; // in kg pro CO2

    private List<DataEnumTransportProperties> transportProperties;


    /**
     *
     * @param startCity
     * @param startLocation
     * @param destinationCity
     * @param destinationLocation
     * @param isReturn
     */
    public DataConnection(String startCity, String startLocation, String destinationCity, String destinationLocation, boolean isReturn) {
        this.startCity = startCity;
        this.startLocation = startLocation;
        this.destinationCity = destinationCity;
        this.destinationLocation = destinationLocation;
        // default values
        this.type = DataEnumTransport.Nothing;
        this.intermediatStations = new ArrayList<>();
        this.company = "noCompany";
        this.companyLogo = 0;
        this.prize = this.calculateRandomPrizeDependingOnType(type);
        this.distance = 0.;
        this.duration = this.calculateRandomDurationDependingOnType(type);
        this.transportProperties = new ArrayList<>();
        this.calculateTransportProperties();
        this.isReturn = isReturn;
    }

    /**
     * Constructor for scenario: travel only forth
     * @param startCity
     * @param startLocation
     * @param destinationCity
     * @param destinationLocation
     * @param startDate
     * @param startTime
     */
    public DataConnection(String startCity, String startLocation, String destinationCity, String destinationLocation, DataDate startDate, DataTime startTime, boolean isReturn) {
        this.startCity = startCity;
        this.startLocation = startLocation;
        this.destinationCity = destinationCity;
        this.destinationLocation = destinationLocation;
        this.startDate = startDate;
        this.startTime = startTime;
        // default values
        this.type = DataEnumTransport.Nothing;
        this.intermediatStations = new ArrayList<>();
        this.company = "noCompany";
        this.companyLogo = 0;
        this.prize = this.calculateRandomPrizeDependingOnType(type);;
        this.distance = 0.;
        this.duration = this.calculateRandomDurationDependingOnType(type);
        this.transportProperties = new ArrayList<>();
        this.calculateTransportProperties();
        this.isReturn = isReturn;
    }

    /**
     * Constructor for scenario: travel back and forth
     * @param startCity
     * @param startLocation
     * @param destinationCity
     * @param destinationLocation
     * @param startDate
     * @param startTime
     * @param returnDate
     * @param returnTime
     */
    public DataConnection(String startCity, String startLocation, String destinationCity, String destinationLocation, DataDate startDate, DataTime startTime, DataDate returnDate, DataTime returnTime, boolean isReturn) {
        this.startCity = startCity;
        this.startLocation = startLocation;
        this.destinationCity = destinationCity;
        this.destinationLocation = destinationLocation;
        this.startDate = startDate;
        this.startTime = startTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        // default values
        this.type = DataEnumTransport.Nothing;
        this.intermediatStations = new ArrayList<>();
        this.company = "noCompany";
        this.companyLogo = 0;
        this.prize = this.calculateRandomPrizeDependingOnType(type);;
        this.distance = 0.;
        this.duration = this.calculateRandomDurationDependingOnType(type);
        this.transportProperties = new ArrayList<>();
        this.calculateTransportProperties();
        this.isReturn = isReturn;
    }

    /**
     * constructor for quick copy
     * @param source
     */
    public DataConnection(DataConnection source){
        this.startCity = source.getStartCity();
        this.startLocation = source.getStartLocation();;
        this.destinationCity = source.getDestinationCity();;
        this.destinationLocation = source.getDestinationLocation();;
        this.startDate = source.getStartDate();;
        this.startTime = source.getStartTime();;
        this.returnDate = source.getReturnDate();;
        this.returnTime = source.getReturnTime();;
        // default values
        this.type = source.getType();
        this.intermediatStations = source.getIntermediatStations();
        this.company = source.getCompany();
        this.companyLogo = source.getCompanyLogo();
        this.prize = source.getPrize();;
        this.distance = source.getDistance();
        this.duration = source.getDuration();
        this.transportProperties = source.getTransportProperties();
        this.isReturn = source.isReturn();
    }

    // internal logic ---------------------------------------------------------------

    /**
     * calculates the amount of stopps (stations) between start and destination
     * @return
     */
    private int calculateIntermediateStations(){
        if(intermediatStations != null && intermediatStations.size() > 0){
            return intermediatStations.size();
        } else {
            return 0;
        }
    }

    /**
     * calculates the CO2-bilance based on the type of transport with random(!) weighted factors
     * (no factors so far are: distance)
     * @return
     */
    private double calculateCO2Bilance(){
        double fundamentalEmission = 0.1234; // kg CO2/km
        switch (this.type) {
            case Car_Sharing:
                return fundamentalEmission * 4;
            case Bus:
                return fundamentalEmission * 2;
            case Ship:
                return fundamentalEmission * 3.5;
            case Train:
                return fundamentalEmission * 2.5;
            case Plane:
                return fundamentalEmission * 6;
            case Mix:
                return fundamentalEmission * ((4+2+3.5+2.5+6)/5);
            default:
                return fundamentalEmission * 1;
        }
    }

    /**
     * calculates the actual date
     * @return DataDate (not null)
     */
    private DataDate calulateActualDate(){
        Date date = new Date();
        return new DataDate(date.getDay(), date.getMonth(), date.getYear());
    }

    /**
     * calculates the actual time
     * @return DataTime (not null)
     */
    private DataTime calculateActualTime(){
        Date date = new Date();
        return new DataTime(date.getMinutes(), date.getHours());
    }

    /**
     * calculates transport properties according to transport type
     */
    private void calculateTransportProperties(){
        switch (this.type) {
            case Car_Sharing:
                this.transportProperties.add(DataEnumTransportProperties.Fast);
                this.transportProperties.add(DataEnumTransportProperties.Reliable);
                this.transportProperties.add(DataEnumTransportProperties.Few_stops);
                break;
            case Bus:
                this.transportProperties.add(DataEnumTransportProperties.Cheap);
                this.transportProperties.add(DataEnumTransportProperties.Eco_friendly);
                break;
            case Ship:
                this.transportProperties.add(DataEnumTransportProperties.Comfortable);
                this.transportProperties.add(DataEnumTransportProperties.Reliable);
                break;
            case Train:
                this.transportProperties.add(DataEnumTransportProperties.Cheap);
                this.transportProperties.add(DataEnumTransportProperties.Comfortable);
                this.transportProperties.add(DataEnumTransportProperties.Eco_friendly);
                break;
            case Plane:
                this.transportProperties.add(DataEnumTransportProperties.Fast);
                this.transportProperties.add(DataEnumTransportProperties.Comfortable);
                this.transportProperties.add(DataEnumTransportProperties.Few_stops);
                break;
            case Mix:
                this.transportProperties.add(DataEnumTransportProperties.Fast);
                this.transportProperties.add(DataEnumTransportProperties.Reliable);
                break;
            default:
                this.transportProperties.add(DataEnumTransportProperties.Nothing);
                break;
        }
    }

    /**
     * calculates a random prize depending on the transport type
     * @param transportType
     * @return
     */
    private double calculateRandomPrizeDependingOnType(DataEnumTransport transportType){
        Random random = new Random();
        int randomElementPrize = random.nextInt(500) + 1;
        switch (transportType){
            case Car_Sharing:
                return randomElementPrize * 0.4;
            case Bus:
                return randomElementPrize * 0.2;
            case Ship:
                return randomElementPrize * 0.7;
            case Train:
                return randomElementPrize * 0.3;
            case Plane:
                return randomElementPrize * 0.8;
            case Mix:
                return randomElementPrize * 0.5;
            default:
                return randomElementPrize;
        }
    }

    /**
     * calculates a random duration depending on the transport type
     * @param transportType
     * @return
     */
    private DataTime calculateRandomDurationDependingOnType(DataEnumTransport transportType){
        Random random = new Random();
        Random random2 = new Random();
        double fastFactor = 0.1;
        int maxHourValue = 5;
        int startvalueHour = 0;
        int maxMinuteValue = 58;
        int startvalueMinute = 1;
        DataTime randomElementDuration = new DataTime(random.nextInt(maxMinuteValue) + startvalueMinute,random2.nextInt(maxHourValue) + startvalueHour);
        switch (transportType){
            case Car_Sharing:
                fastFactor = 1.2;
                randomElementDuration = new DataTime((int)((random.nextInt(maxMinuteValue) + startvalueMinute)*fastFactor),(int)((random2.nextInt(maxHourValue)+startvalueHour)*fastFactor));
                return randomElementDuration;
            case Bus:
                fastFactor = 1.9;
                randomElementDuration = new DataTime((int)((random.nextInt(maxMinuteValue) + startvalueMinute)*fastFactor),(int)((random2.nextInt(maxHourValue)+startvalueHour)*fastFactor));
                return randomElementDuration;
            case Ship:
                fastFactor = 1.7;
                randomElementDuration = new DataTime((int)((random.nextInt(maxMinuteValue) + startvalueMinute)*fastFactor),(int)((random2.nextInt(maxHourValue)+startvalueHour)*fastFactor));
                return randomElementDuration;
            case Train:
                fastFactor = 1.8;
                randomElementDuration = new DataTime((int)((random.nextInt(maxMinuteValue) + startvalueMinute)*fastFactor),(int)((random2.nextInt(maxHourValue)+startvalueHour)*fastFactor));
                return randomElementDuration;
            case Plane:
                fastFactor = 1.1;
                randomElementDuration = new DataTime((int)((random.nextInt(maxMinuteValue) + startvalueMinute)*fastFactor),(int)((random2.nextInt(maxHourValue)+startvalueHour)*fastFactor));
                return randomElementDuration;
            case Mix:
                fastFactor = 1.5;
                randomElementDuration = new DataTime((int)((random.nextInt(maxMinuteValue) + startvalueMinute)*fastFactor),(int)((random2.nextInt(maxHourValue)+startvalueHour)*fastFactor));
                return randomElementDuration;
            default:
                fastFactor = 1.7;
                randomElementDuration = new DataTime((int)((random.nextInt(maxMinuteValue) + startvalueMinute)*fastFactor),(int)((random2.nextInt(maxHourValue)+startvalueHour)*fastFactor));
                return randomElementDuration;
        }
    }

    // SETTER -----------------------------------------------------------------------
    public void setIntermediatStations(List<DataConnection> intermediatStations) {
        this.intermediatStations = intermediatStations;
    }

    public void setType(DataEnumTransport type) {
        this.type = type;
        this.prize = this.calculateRandomPrizeDependingOnType(this.type);
        this.duration = this.calculateRandomDurationDependingOnType(this.type);
        this.calculateTransportProperties();
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCompanyLogo(int companyLogo) {
        this.companyLogo = companyLogo;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDuration(DataTime duration) {
        this.duration = duration;
    }


    // GETTER -----------------------------------------------------------------------
    public String getStartCity() {
        return startCity;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public DataDate getStartDate() {
        if(startDate == null){
            return this.calulateActualDate();
        } else {
            return startDate;
        }
    }

    public DataTime getStartTime() {
        if(startTime == null){
            return this.calculateActualTime();
        } else {
            return startTime;
        }
    }

    public DataDate getReturnDate() {
        if(returnDate == null){
            return this.calulateActualDate();
        } else {
            return returnDate;
        }
    }

    public DataTime getReturnTime() {
        if(returnTime == null){
            return this.calculateActualTime();
        } else {
            return returnTime;
        }
    }

    public List<DataConnection> getIntermediatStations() {
        return intermediatStations;
    }

    public String getCompany() {
        return company;
    }

    public int getCompanyLogo() {
        return companyLogo;
    }

    public DataTime getDuration() {
        return duration;
    }

    public double getPrize() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        String formattedNumber = decimalFormat.format(prize);
        double roundedNumber = Double.parseDouble(formattedNumber);
        return roundedNumber;
    }

    public DataEnumTransport getType() {
        return type;
    }

    public int getSwitchTransfer() {
        return this.calculateIntermediateStations();
    }

    public double getCO2Bilance() {
        return this.calculateCO2Bilance();
    }

    public double getDistance() {
        return distance;
    }

    public List<DataEnumTransportProperties> getTransportProperties() {
        return transportProperties;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public boolean hasTransportProperty(DataEnumTransportProperties property){
        Log.d("malte6", "hasTransportProperty(): this.transportProperties(): " + this.transportProperties.toString() + " with analyzer: " + property + ", result = " +this.transportProperties.contains(property));
        return this.transportProperties.contains(property);
    }

    @Override
    public String toString() {
        return "DataConnection{" +
                "startCity='" + startCity + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", destinationLocation='" + destinationLocation + '\'' +
                ", startDate=" + startDate +
                ", startTime=" + startTime +
                ", returnDate=" + returnDate +
                ", returnTime=" + returnTime +
                ", intermediatStations=" + intermediatStations +
                ", company='" + company + '\'' +
                ", companyLogo=" + companyLogo +
                ", duration=" + duration +
                ", prize=" + prize +
                ", type=" + type +
                ", switchTransfer=" + switchTransfer +
                ", CO2Bilance=" + CO2Bilance +
                ", distance=" + distance +
                ", transportProperties=" + transportProperties +
                '}';
    }

    public String toStringShort() {
        return "(City: " + startCity +
                ", time: " + startTime + ") ";

    }

    @Override
    public boolean equals(Object o) {
        DataConnection compareData = (DataConnection) o;
        if(this.startCity!=compareData.startCity)
            return false;
        if(this.startLocation!=compareData.startLocation)
            return false;
        if(this.destinationCity!=compareData.destinationCity)
            return false;
        if(this.destinationLocation!=compareData.destinationLocation)
            return false;
        if(!this.startDate.equals(compareData.startDate))
            return false;
        if(!this.startTime.equals(compareData.startTime))
            return false;
        if(this.duration!=compareData.duration)
            return false;
        if(this.prize!=compareData.prize)
            return false;
        if(this.CO2Bilance!=compareData.CO2Bilance)
            return false;
        return true;
    }

    DataTime getReturnTimeWithDuration(){
        int startHour=startTime.getHour();
        int startMinute=startTime.getMinute();
        int durationHour=duration.getHour();
        int durationMinute=duration.getMinute();

        int returnMinute=0;
        int returnHour=0;
        if(startMinute+durationMinute>=60){
            returnMinute+=startMinute+durationMinute-60;
            returnHour+=1;
        }
        else
            returnMinute=startMinute+durationMinute;

        if(returnHour+startHour+durationHour>=24)
            returnHour+=startHour+durationHour-24;
        else
            returnHour+=startHour+durationHour;

        return new DataTime(returnMinute,returnHour);
    }

    boolean isReturnOnNextDay(){
        int startHour=startTime.getHour();
        int startMinute=startTime.getMinute();
        int durationHour=duration.getHour();
        int durationMinute=duration.getMinute();

        int returnMinute=0;
        int returnHour=0;
        if(startMinute+durationMinute>=60){
            returnMinute+=startMinute+durationMinute-60;
            returnHour+=1;
        }
        else
            returnMinute=startMinute+durationMinute;

        if(returnHour+startHour+durationHour>=24)
            return true;
        return false;
    }

}
