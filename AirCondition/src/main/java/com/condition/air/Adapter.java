package com.condition.air;

import com.condition.air.Store.Data.IndexData;
import com.condition.air.Store.Data.SensorData;
import com.condition.air.Store.Data.StationData;
import com.condition.air.Store.Data.ValuesData;
import com.condition.air.Store.Subclasses.IndexLevel;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Adapter Class which transform JSON Data into Concrete classes.
 *
 * @author Dawid Majchrowski
 */

public class Adapter {
    public static final String stationsPath = System.getProperty("user.dir") + "/src/main/resources/station/stations.txt";

    /**
     * getStation method transfor Path into Map
     *
     * @param Path path to given file
     * @throws FileNotFoundException when file is not found
     * @return Map with StationNames mapped into Station class
     */
    public Map<String, Station> getStations(String Path) throws FileNotFoundException {
        Map<String, Station> result = new HashMap<>();
        JsonReader reader = new JsonReader(new FileReader(Path));
        Gson gson = new Gson();
        StationData[] stationsData = gson.fromJson(reader, StationData[].class);
        for (StationData stationData : stationsData) {
            Station station = new Station(stationData);
            result.put(stationData.stationName, station);
        }
        return result;
    }

    /**
     * getIndex method Transform indexFilePath into indexFile
     *
     * @param Path path to given file
     * @throws FileNotFoundException when file is not found
     * @return IndexLevel
     */

    public IndexLevel getIndex(String Path) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(Path));
        Gson gson = new Gson();
        IndexData indexData = gson.fromJson(reader, IndexData.class);
        return indexData.stIndexLevel;
    }

    /**
     * getSensor method Transform Path into SensorData
     *
     * @param Path path to given file
     * @throws FileNotFoundException when file is not found
     * @return SensorData array
     */
    public SensorData[] getSensor(String Path) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(Path));
        Gson gson = new Gson();
        SensorData[] sensorData = gson.fromJson(reader, SensorData[].class);
        return sensorData;
    }

    /**
     * getIndex method Transform Path into ValesData
     *
     * @param Path path to given file
     * @throws FileNotFoundException when file is not found
     * @return ValuesData
     */
    public ValuesData getValueData(String Path) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(Path));
        ValuesData valuesData = gson.fromJson(reader, ValuesData.class);
        return valuesData;
    }
}
