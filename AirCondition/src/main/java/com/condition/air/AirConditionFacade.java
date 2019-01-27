package com.condition.air;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Facade class, which has all the API methods for the APP.
 *
 * @author Dawid Majchrowski
 */
public class AirConditionFacade {
    Stations stations;

    /**
     * Constructor that building the stations attribute
     *
     */
    AirConditionFacade() {
        stations = new Stations();
        stations.build();
    }

    /**
     * Method that print air index in station that has took measurement
     *
     * @param stationName name of station that measured index.
     */
    public void printIndex(String stationName) {
        stations.getIndex(stationName);
        System.out.println("Index for station " + stationName + ": " + stations.getIndex(stationName));
    }


    /**
     * Method that print value of concrete parameter for given station and date
     *
     * @param parameter Conrete parameter
     * @param date Concrete date
     * @param stationName Name of station that measured data.
     * @param force Force the update of current cache files
     */
    public void printValueOfParameter(String parameter, String stationName, String date, boolean force) {
        if (stations.stations.get(stationName) == null) {
            System.out.println("No station found");
            throw new IllegalArgumentException();
        }
        if (!Norm.parameterValue.keySet().contains(parameter)) {
            System.out.println("Wrong parameter given");
            throw new IllegalArgumentException();
        }
        Double value = stations.stations.get(stationName).findValue(parameter, date, force);
        if (value == null) {
            System.out.println("No value found for given key date and station");
        } else
            System.out.println("Station name: " + stationName +
                    ",\nParameter: " + parameter +
                    ",\nDate " + date +
                    ",\nValue: " + String.valueOf(value));
    }

    /**
     * Method that print value of concrete parameter for given station and date
     *
     * @param parameter Conrete parameter
     * @param startDate Start of period
     * @param endDate End of period
     * @param stationName Name of station that measured data.
     * @param force Force the update of current cache files
     */
    public void printAverageValue(String parameter, String stationName, String startDate, String endDate, boolean force) {
        if (stations.stations.get(stationName) == null) {
            System.out.println("No station found");
            throw new IllegalArgumentException();
        }
        if (!Norm.parameterValue.keySet().contains(parameter)) {
            System.out.println("Wrong parameter given");
            throw new IllegalArgumentException();
        }
        Double value = stations.stations.get(stationName).findAverageValue(parameter, startDate, endDate, force);
        System.out.println("Station name: " + stationName +
                ",\nParameter: " + parameter +
                ",\nStartDate " + startDate +
                ",\nEndDate " + endDate +
                ",\nAverage Value: " + String.valueOf(value));
    }

    /**
     * Method that print fluctuaction of concrete parameter for given station and date
     *
     * @param date Date from where we start masure fluctuactions
     * @param stations Stations names to be checked
     * @param force Force the update of current cache files
     */
    public void printMaxFluctuations(String[] stations, String date, boolean force) {
        FluctuationsFinderVisitor maxFluctuations = this.stations.findMaxFluctuations(stations, date, force);
        if (maxFluctuations == null) {
            System.out.println("No fluctuations found for given date " + date);
        } else System.out.println("In station: " + maxFluctuations.stationName +
                "\nParameter: " + maxFluctuations.key +
                "\nSience date: " + date + " untill now" +
                "\nMax fluctuations value: " + maxFluctuations.maxFluctuations);
    }

    /**
     * Method that print fluctuaction of concrete parameter for given station and date
     *
     * @param date Date at which we search for min value
     * @param force Force the update of current cache files
     */
    public void findMinParameter(String date, boolean force) {
        if (force)
            this.stations.rebuild();
        ValueAtDateVisitor value = stations.findMinValueAtDate(date, force);
        if (value == null) {
            System.out.println("Wrong date was given");
            throw new IllegalArgumentException();
        }
        if (value.value == null) {
            System.out.println("Wrong date was given");
            throw new IllegalArgumentException();
        }
        System.out.println("In station: " + value.station +
                "\nat date " + date +
                "\nparameter " + value.key +
                "\nhas the lowest value " + value.value);
    }

    /**
     * Method that print N parameters that exceeded norm
     *
     * @param date Date from where we start measare fluctuactions
     * @param station station that holds the sensors
     * @param N max number of parameters to be printed
     * @param force Force the update of current cache files
     */

    public void findNParametersOverNorm(String station, String date, Integer N, boolean force) {
        if (N < 1 || N > 7) {
            System.out.println("Number of parameter must be integer number from 1 to 7");
            throw new IllegalArgumentException();
        }
        TreeMap<Double, String> result = stations.stations.get(station).findParametersOverNorm(date, force);
        if (result.entrySet().size() == 0) {
            System.out.println("All parameters are in the ballpark");
        } else {
            int counter = 0;
            for (Map.Entry<Double, String> entry : result.entrySet()) {
                if (counter == N) break;
                System.out.println("Exceeded value of parameter " + entry.getValue()
                        + " is equal " + String.valueOf(entry.getKey())
                        + ", Norm is " + String.valueOf(Norm.parameterValue.get(entry.getValue()))
                        );
                ++counter;

            }
        }

    }

    /**
     * Method that print min and max value of given parameter
     *
     * @param parameter given parameter to search
     * @param force Force the update of current cache files
     */
    public void findMinAndMax(String parameter, boolean force) {
        if (!Norm.parameterValue.keySet().contains(parameter)) {
            System.out.println("Wrong parametr was given");
            throw new IllegalArgumentException();
        }
        if (force)
            this.stations.rebuild();
        FindMaxValueVisitor max = stations.findMaxValue(parameter, force);
        if (max == null || max.value == null) System.out.println("Max value not found");
        else {
            System.out.println("Parameter: " + parameter
                    + "\nMax Value: " + max.value
                    + "\nDate: " + max.date
                    + "\nFound in Station " + max.name);
        }
        FindMinValueVisitor min = stations.findMinValue(parameter, false);
        if (min == null || min.value == null) System.out.println("Min value not found");
        else {
            System.out.println("Parameter: " + parameter
                    + "\nMin value: " + min.value
                    + "\nDate: " + min.date
                    + "\nFound in Station " + min.name);
        }
    }

    /**
     * Method that print min and max value of given parameter
     *
     * @param parameter given parameter to be printed
     * @param force Force the update of current cache files
     * @param endDate end date of chart
     * @param startDate start date of chart
     * @param stations stations to be searched
     */
    public void printChart(String parameter, String[] stations, String startDate, String endDate, boolean force) {
        if (force)
            this.stations.rebuild();
        Map<String, List<String>> result = this.stations.printChart(stations, startDate, endDate, parameter, force);
        System.out.println("For parameter " + parameter);
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            for (String string : entry.getValue()) {
                System.out.println(entry.getKey() + " " + string);
            }
        }
    }

    /**
     * Method that print name of all stations in given city
     *
     * @param city name of the city to find stations
     */

    public void printByCity(String city) {
        List<String> result = this.stations.getByCity(city);
        if (result.size() == 0) {
            System.out.println("No station for city " + city + " found");
            throw new IllegalArgumentException();
        }
        for (String station : result)
            System.out.println(station);
    }

    /**
     * Method that print name of all parameters
     *
     */

    public void printParameters() {
        for (String parameter : Norm.parameterValue.keySet())
            System.out.println(parameter);
    }
}


