package com.condition.air;

import com.condition.air.Store.Subclasses.IndexLevel;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Stations class, which represents concrete Stations.
 *
 * @author Dawid Majchrowski
 */

public class Stations {
    Map<String, Station> stations;

    /**
     * Lazy stations update
     */
    public void build() {
        Map<String, Station> tmpStations = null;
        Adapter stationsAdapter = new Adapter();
        try {
            tmpStations = stationsAdapter.getStations(stationsAdapter.stationsPath);
        } catch (FileNotFoundException e) {
            update();
            try {
                tmpStations = stationsAdapter.getStations(stationsAdapter.stationsPath);
            } catch (FileNotFoundException e1) {
                System.err.println("No file found in resources");
            }
        } finally {
            stations = tmpStations;
        }
    }

    /**
     * Lazy stations update
     */
    public void update() {
        Loader.Update(Loader.giosStation, "./src/main/resources/station/", "stations.txt", Loader.methodGet);
    }

    /**
     * Lazy stations rebuild
     */
    public void rebuild() {
        this.update();
        this.build();
    }

    public IndexLevel getIndex(String stationName) {
        Station station = stations.get(stationName);
        if (station == null) {
            this.rebuild();
            station = stations.get(stationName);
            if (station == null) {
                System.out.println("No station " + stationName + " found");
                throw new IllegalArgumentException();
            }
        }
        IndexLevel indexLevel = stations.get(stationName).getIndex();
        if (indexLevel == null) {
            System.out.println("For station " + stationName + " no index found");
            throw new IllegalArgumentException();
        }
        return indexLevel;
    }

    public FluctuationsFinderVisitor findMaxFluctuations(String[] stationNames, String startDate, boolean force) {
        if (force) rebuild();
        for (String name : stationNames) {
            if (!stations.containsKey(name)) {
                System.out.println("No station named " + name);
                throw new IllegalArgumentException();
            }
        }

        FluctuationsFinderVisitor maxFluctuations = null;
        for (String name : stationNames) {
            FluctuationsFinderVisitor tmp = stations.get(name).findMaxFluctuations(startDate, name, force);
                if (maxFluctuations == null ||
                        maxFluctuations.maxFluctuations == null ||
                        (tmp != null && tmp.maxFluctuations != null
                                && maxFluctuations.maxFluctuations > tmp.maxFluctuations))
                maxFluctuations = tmp;
        }
        return maxFluctuations;
    }

    public ValueAtDateVisitor findMinValueAtDate(String date, boolean force) {
        ValueAtDateVisitor result = null;
        for (Map.Entry<String, Station> entry : stations.entrySet()) {
            ValueAtDateVisitor valueAtDateVisitor = entry.getValue().findMinValueAtDate(date, entry.getKey(), force);
            if (result == null || result.value == null || (valueAtDateVisitor != null && valueAtDateVisitor.value != null && result.value > valueAtDateVisitor.value))
                result = valueAtDateVisitor;
        }
        return result;
    }

    public FindMaxValueVisitor findMaxValue(String param, boolean force) {
        FindMaxValueVisitor result = null;
        for (Station station : stations.values()) {
            FindMaxValueVisitor findMaxValueVisitor = station.findMaxValue(param, force);
            if (result == null) result = findMaxValueVisitor;
            else {
                if (result.value == null) result = findMaxValueVisitor;
                else if (findMaxValueVisitor.value != null && findMaxValueVisitor.value > result.value) {
                    result = findMaxValueVisitor;
                }
            }
        }
        return result;
    }

    public FindMinValueVisitor findMinValue(String param, boolean force) {
        FindMinValueVisitor result = null;
        for (Station station : stations.values()) {
            FindMinValueVisitor findMinValueVisitor = station.findMinValue(param, force);
            if (result == null) result = findMinValueVisitor;
            else {
                if (result.value == null) result = findMinValueVisitor;
                else if (findMinValueVisitor.value != null && findMinValueVisitor.value < result.value) {
                    result = findMinValueVisitor;
                }
            }
        }
        return result;
    }

    public Map<String, List<String>> printChart(String stations[], String startDate, String endDate, String key, boolean force) {
        for (String name : stations) {
            if (!this.stations.keySet().contains(name)) {
                System.out.println("No station named " + name);
                throw new IllegalArgumentException();
            }
        }
        Map<String, List<String>> result = new TreeMap<>();
        for (String station : stations) {
            this.stations.get(station).printChart(endDate, startDate, station, key, result, force);
        }
        return result;
    }

    public List<String> getByCity(String city) {
        List<String> result = new LinkedList<>();
        for (Station station : stations.values())
            station.getByCity(result, city);
        return result;
    }
}