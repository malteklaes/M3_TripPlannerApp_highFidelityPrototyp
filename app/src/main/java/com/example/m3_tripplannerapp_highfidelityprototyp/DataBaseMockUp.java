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



    // ADDER -----------------------------------------------------------------------

    /**
     * adds a new whole 24h day with a specific start-destination route and a transportType
     * hourly intervall
     * @param startCity
     * @param startLocation
     * @param destinationCity
     * @param destinationLocation
     * @param startDate
     * @param transportType
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
     * @param transportType
     * @param company
     * @param prize
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
     * @param transportType
     * @param prize
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


    // RETRIEVER -----------------------------------------------------------------------

    /**
     * retrieves data which is later than this date
     * @param database
     * @param date
     * @return
     */
    public List<DataConnection> retrieveDataByStartDateLater(List<DataConnection> database, DataDate date){
        List<DataConnection> result = new ArrayList<>();
        for (DataConnection connection : database) {
            if(connection.getStartDate().compareThisDateToThatDate(date) == DataEnumTimeComparison.Later){
                result.add(connection);
            }
        }
        return result;
    }

    /**
     * retrieves data which is equal than this date
     * @param database
     * @param date
     * @return
     */
    public List<DataConnection> retrieveDataByStartDateEqual(List<DataConnection> database, DataDate date){
        List<DataConnection> result = new ArrayList<>();
        for (DataConnection connection : database) {
            if(connection.getStartDate().compareThisDateToThatDate(date) == DataEnumTimeComparison.Equal){
                result.add(connection);
            }
        }
        return result;
    }

    /**
     * retrieves data which is earlier than this date
     * @param database
     * @param date
     * @return
     */
    public List<DataConnection> retrieveDataByStartDateEarlier(List<DataConnection> database, DataDate date){
        List<DataConnection> result = new ArrayList<>();
        for (DataConnection connection : database) {
            if(connection.getStartDate().compareThisDateToThatDate(date) == DataEnumTimeComparison.Earlier){
                result.add(connection);
            }
        }
        return result;
    }

    /**
     * retrieves data which is later than this time
     * @param database
     * @param time
     * @return
     */
    public List<DataConnection> retrieveDataByStartTimeLater(List<DataConnection> database, DataTime time){
        List<DataConnection> result = new ArrayList<>();
        for (DataConnection connection : database) {
            if(connection.getStartTime().compareThisTimeToThatTime(time) == DataEnumTimeComparison.Later){
                result.add(connection);
            }
        }
        return result;
    }

    /**
     * retrieves data which is earlier than this time
     * @param database
     * @param time
     * @return
     */
    public List<DataConnection> retrieveDataByStartTimeEarlier(List<DataConnection> database, DataTime time){
        List<DataConnection> result = new ArrayList<>();
        for (DataConnection connection : database) {
            if(connection.getStartTime().compareThisTimeToThatTime(time) == DataEnumTimeComparison.Earlier){
                result.add(connection);
            }
        }
        return result;
    }

    /**
     * looks out for any property and those connections which incorporates this property to the result
     * @param database
     * @param transportProperties
     * @return
     */
    public List<DataConnection> retrieveDataByTransportProperties(List<DataConnection> database, List<DataEnumTransportProperties> transportProperties){
        List<DataConnection> result = new ArrayList<>();
        for (DataConnection connection : database) {
            for (DataEnumTransportProperties property: transportProperties) {
                if(connection.hasTransportProperty(property)){
                    result.add(connection);
                }
            }
        }
        return result;
    }


    /**
     * filters database due to startCity and startLocation
     * @param database
     * @param startCity
     * @param startLocation
     * @return
     */
    public List<DataConnection> retrieveDataByStartCityAndLocation(List<DataConnection> database, String startCity, String startLocation){
        List<DataConnection> result = new ArrayList<>();
        for (DataConnection connection : database) {
            if(connection.getStartCity().equals(startCity) && connection.getStartLocation().equals(startLocation)){
                result.add(connection);
            }
        }
        return result;
    }

    // FILTER FUNCTIONS -----------------------------------------------------------------------
    public List<DataConnection> filterByParameters(List<DataConnection> database, List<DataEnumTransportProperties> filterProperties, DataDate filterDate, DataTime filterTime, String filterCity, String filterLocation){
        List<DataConnection> filteredResult = new ArrayList<>();
        // [1] filter by property
        filteredResult = this.retrieveDataByTransportProperties(database, filterProperties);
        // [2] fitler by date
        filteredResult = this.retrieveDataByStartDateEqual(filteredResult, filterDate);
        // [3] fitler by time
        filteredResult = this.retrieveDataByStartTimeLater(filteredResult, filterTime);
        // [4] fitler by city and location
        filteredResult = this.retrieveDataByStartCityAndLocation(filteredResult, filterCity, filterLocation);
        return filteredResult;
    }


    // HELP FUNCTIONS -----------------------------------------------------------------------
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

    public List<DataConnection> getDataBaseMockUp() {
        return dataBaseMockUp;
    }

    public void setDataBaseMockUp(List<DataConnection> dataBaseMockUp) {
        this.dataBaseMockUp = dataBaseMockUp;
    }

    @Override
    public String toString() {
        return "DataBaseMockUp{" +
                "dataBaseMockUp=" + dataBaseMockUp.toString() +
                '}';
    }

    public String toStringShort(){
        String result = "DataBaseMockUp{ ";
        for (DataConnection connection: dataBaseMockUp) {
            result += connection.toStringShort();
        }
        return result + " }";
    }
}
