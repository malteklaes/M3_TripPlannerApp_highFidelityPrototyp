package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


    public DataConnection(String startCity, String startLocation, String destinationCity, String destinationLocation) {
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
        this.duration = new DataTime(0,0);
        this.transportProperties = new ArrayList<>();
        this.calculateTransportProperties();
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
    public DataConnection(String startCity, String startLocation, String destinationCity, String destinationLocation, DataDate startDate, DataTime startTime) {
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
        this.duration = new DataTime(0,0);
        this.transportProperties = new ArrayList<>();
        this.calculateTransportProperties();
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
    public DataConnection(String startCity, String startLocation, String destinationCity, String destinationLocation, DataDate startDate, DataTime startTime, DataDate returnDate, DataTime returnTime) {
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
        this.duration = new DataTime(0,0);
        this.transportProperties = new ArrayList<>();
        this.calculateTransportProperties();
    }

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
            case Car:
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

    private void calculateTransportProperties(){
        switch (this.type) {
            case Car:
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
                this.transportProperties.add(DataEnumTransportProperties.Eco_friendly);
                this.transportProperties.add(DataEnumTransportProperties.Cheap);
                this.transportProperties.add(DataEnumTransportProperties.Reliable);
                break;
        }
    }

    private double calculateRandomPrizeDependingOnType(DataEnumTransport transportType){
        Random random = new Random();
        int randomElementPrize = random.nextInt(500) + 1;
        switch (transportType){
            case Car:
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

    // SETTER -----------------------------------------------------------------------

    public void setIntermediatStations(List<DataConnection> intermediatStations) {
        this.intermediatStations = intermediatStations;
    }

    public void setType(DataEnumTransport type) {
        this.type = type;
        this.prize = this.calculateRandomPrizeDependingOnType(this.type);
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
}
