package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.util.Log;


import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * mocks a fictive database with random entries (dataConnections)
 */
public class DataBaseMockUp {

    List<DataConnection> dataBaseMockUp;


    /**
     * generates a database with fictive, random database-entries
     */
    public DataBaseMockUp(){
        this.dataBaseMockUp = new ArrayList<>();
        this.generateRandomDates();
    }

    /**
     * initialize a database with certain cities, a random prize, random minute-intervall and a random company
     * @param startCity
     * @param startLocation
     * @param destinationCity
     * @param destinationLocation
     */
    public DataBaseMockUp(String startCity, String startLocation, String destinationCity, String destinationLocation, DataDate startDate, DataEnumTransport transportType) {
        this.dataBaseMockUp = new ArrayList<>();
        Random random = new Random();
        int randomPrize = random.nextInt(500); // in euro
        String company = "randomTransportCompany";
        this.addAnotherDay(startCity, startLocation, destinationCity, destinationLocation, startDate, transportType, company, randomPrize);
    }

    /**
     * adds a new whole 24h day with a specific start-destination route and a transportType
     * hourly intervall
     * @param startCity
     * @param startLocation
     * @param destinationCity
     * @param destinationLocation
     * @param startDate
     */
    public void addAnotherDay(String startCity, String startLocation, String destinationCity, String destinationLocation, DataDate startDate, DataEnumTransport transportType){
        Random random = new Random();
        int randomMinute = random.nextInt(60);
        for (int i = 0; i < 24; i++) {
            DataConnection connection = new DataConnection(startCity, startLocation, destinationCity, destinationLocation, startDate, new DataTime(randomMinute,i));
            connection.setType(transportType);
            dataBaseMockUp.add(connection);

        }
    }

    /**
     * adds a new whole 24h day with a specific start-destination route and a transportType, company, prize
     * hourly intervall
     * @param startCity
     * @param startLocation
     * @param destinationCity
     * @param destinationLocation
     * @param startDate
     */
    public void addAnotherDay(String startCity, String startLocation, String destinationCity, String destinationLocation, DataDate startDate, DataEnumTransport transportType, String company, double prize){
        Random random = new Random();
        int randomMinute = random.nextInt(60);
        for (int i = 0; i < 24; i++) {
            DataConnection connection = new DataConnection(startCity, startLocation, destinationCity, destinationLocation, startDate, new DataTime(randomMinute,i));
            connection.setType(transportType);
            connection.setPrize(prize);
            connection.setCompany(company);
            dataBaseMockUp.add(connection);
        }
    }

    /**
     * adds a new whole 24h day with a specific start-destination route and a transportType, prize
     * hourly intervall
     * @param startCity
     * @param startLocation
     * @param destinationCity
     * @param destinationLocation
     * @param startDate
     */
    public void addAnotherDay(String startCity, String startLocation, String destinationCity, String destinationLocation, DataDate startDate, DataEnumTransport transportType, double prize){
        Random random = new Random();
        int randomMinute = random.nextInt(60);

        for (int i = 0; i < 24; i++) {
            DataConnection connection = new DataConnection(startCity, startLocation, destinationCity, destinationLocation, startDate, new DataTime(randomMinute,i));
            connection.setType(transportType);
            connection.setPrize(prize);
            dataBaseMockUp.add(connection);
        }
    }

    /**
     * adds multiple connections to this database
     * @param connections
     */
    public void addConnections(List<DataConnection> connections){
        this.dataBaseMockUp.addAll(connections);
    }



    private void generateRandomDates(){
        DataEnumTransport[] transports = DataEnumTransport.values();


        Random randomTransport1 = new Random();
        int index1 = randomTransport1.nextInt(transports.length);
        this.addAnotherDay("vienna", "hbf", "frankfurt", "hbf", this.calculateRandomDate(), transports[index1]);

        Random randomTransport2 = new Random();
        int index2 = randomTransport2.nextInt(transports.length);
        this.addAnotherDay("frankfurt", "hbf", "vienna", "hbf", this.calculateRandomDate(), transports[index2]);

        Random randomTransport3 = new Random();
        int index3 = randomTransport3.nextInt(transports.length);
        this.addAnotherDay("vienna", "hbf", "paris", "Gare du Nord", this.calculateRandomDate(), transports[index3]);

        Random randomTransport4 = new Random();
        int index4 = randomTransport4.nextInt(transports.length);
        this.addAnotherDay("paris", "Gare du Nord", "vienna", "hbf", this.calculateRandomDate(), transports[index4]);


        /*DataConnection probe = new DataConnection("bla","bla","bla","bla");
        boolean getEarliestTime = false;
        for (DataConnection connect : dataBaseMockUp) {
            if(!getEarliestTime && connect.getStartCity().equals("vienna") && connect.getDestinationCity().equals("paris") && connect.getStartDate().compareThisDateToThatDate(this.calulateActualDate()) == DataEnumTimeComparison.Equal && connect.getDestinationCity().equals("paris") && connect.getStartTime().compareThisTimeToThatTime(new DataTime(27,5)) == DataEnumTimeComparison.Later){
                probe = connect;
                getEarliestTime = true;
            }
        }

        Log.d("malte", "Ergebnis vienna->paris: " + probe.toString());*/
    }


    /**
     * calculates random date
     * @return
     */
    private DataDate calculateRandomDate(){
        // random day
        Random randomD = new Random();
        int randomDay = randomD.nextInt(28);
        randomDay = (randomDay == 0) ? 1 : randomDay;
        // random month
        Random randomM = new Random();
        int randomMonth = randomM.nextInt(12);
        randomMonth = (randomMonth == 0) ? 1 : randomMonth;

        return new DataDate(randomDay, randomMonth, 2023);
    }


    @Override
    public String toString() {
        return "DataBaseMockUp{" +
                "dataBaseMockUp=" + dataBaseMockUp.toString() +
                '}';
    }
}
