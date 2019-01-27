package com.condition.air;

import com.condition.air.Store.Data.SensorData;
import com.condition.air.Store.Data.StationData;
import com.condition.air.Store.Data.ValuesData;
import com.condition.air.Store.Subclasses.IndexLevel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Station class, which represents concrete Station.
 *
 * @author Dawid Majchrowski
 */

public class Station {
    public List<ParamValues> paramValuesList;
    private StationData stationData;
    private IndexLevel indexLevel;
    private Adapter adapter;

    /**
     * Constructor loads data to Station
     *
     * @param stationData stationData given by adapter
     */
    public Station(StationData stationData) {
        this.stationData = stationData;
        this.adapter = new Adapter();
        this.paramValuesList = new ArrayList<>();
    }

    /**
     * Lazy building the values for concrete station
     *
     * @param force force the update
     */
    public void buildValues(boolean force) {
        String path = System.getProperty("user.dir") + "/src/main/resources/sensors/sensor" + stationData.id + ".txt";
        SensorData[] sensorData = null;
        try {
            sensorData = adapter.getSensor(path);
        } catch (FileNotFoundException e) {
            sensorUpdate();
            try {
                sensorData = adapter.getSensor(path);
            } catch (FileNotFoundException e2) {
                System.err.println("File" + path + " not found");
                throw new IllegalArgumentException();
            }
        }
        for (SensorData sensor : sensorData) {
            String dataPath = System.getProperty("user.dir") + "/src/main/resources/data/data" + sensor.id + ".txt";
            ValuesData valuesData = null;
            if (force) dataUpdate(sensor.id);
            try {
                valuesData = adapter.getValueData(dataPath);
            } catch (FileNotFoundException e3) {
                dataUpdate(sensor.id);
                try {
                    valuesData = adapter.getValueData(dataPath);
                } catch (FileNotFoundException e4) {
                    System.err.println("File" + dataPath + " not found");
                    throw new IllegalArgumentException();
                }
            }
            if (valuesData == null) {
                System.err.println("Couldn't find station " + stationData + " sensor " + sensor.id + " Parameters Data");
                throw new IllegalArgumentException();
            }
            paramValuesList.add(0, new ParamValues(valuesData));
        }
    }
    /**
     * Lazy building the params for concrete station
     *
     * @param param param to be updated
     */
    public void buildParam(String param) {
        String path = System.getProperty("user.dir") + "/src/main/resources/sensors/sensor" + stationData.id + ".txt";
        SensorData[] sensorData = null;
        try {
            sensorUpdate();
            sensorData = adapter.getSensor(path);
        } catch (FileNotFoundException e) {
            System.err.println("File" + path + " not found");
            throw new IllegalArgumentException();
        }
        for (SensorData sensor : sensorData) {
            if (sensor.param.paramCode.equals(param)) {
                String dataPath = System.getProperty("user.dir") + "/src/main/resources/data/data" + sensor.id + ".txt";
                ValuesData valuesData = null;
                dataUpdate(sensor.id);
                try {
                    valuesData = adapter.getValueData(dataPath);
                } catch (FileNotFoundException e4) {
                    System.err.println("File" + dataPath + " not found");
                    throw new IllegalArgumentException();
                }
                if (valuesData == null) {
                    System.err.println("Couldn't find station " + stationData + " sensor " + sensor.id + " Parameters Data");
                    throw new IllegalArgumentException();
                }
                paramValuesList.add(0, new ParamValues(valuesData));
                break;
            }
        }
    }

    /**
     * Lazy updating data
     *
     * @param id to be appended to the end of fileName
     */
    public void dataUpdate(int id) {
        Loader.Update(Loader.giosData + id, System.getProperty("user.dir") + "/src/main/resources/data/", "data" + id + ".txt", Loader.methodGet);
    }

    /**
     * Lazy updating sensor
     *
     */
    public void sensorUpdate() {
        Loader.Update(Loader.giosSensor + stationData.id, System.getProperty("user.dir") + "/src/main/resources/sensors/", "sensor" + stationData.id + ".txt", Loader.methodGet);

    }

    /**
     * Lazy updating index
     *
     */
    public void indexUpdate() {
        Loader.Update(Loader.giosIndex + stationData.id, System.getProperty("user.dir") + "/src/main/resources/index/", "index" + stationData.id + ".txt", Loader.methodGet);
    }

    /**
     * Setter for index
     *
     */
    public void setIndex() {
        try {
            indexLevel = adapter.getIndex(System.getProperty("user.dir") + "/src/main/resources/index/index" + stationData.id + ".txt");
        } catch (FileNotFoundException e) {
            indexUpdate();
            try {
                indexLevel = new Adapter().getIndex(System.getProperty("user.dir") + "/src/main/resources/index/index" + stationData.id + ".txt");
            } catch (FileNotFoundException e2) {
                System.err.println("Index file not found");
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Getter for index
     *
     * @return Nothing
     */

    public IndexLevel getIndex() {
            indexUpdate();
            setIndex();
        return indexLevel;
    }

    /**
     * Finding value
     *
     * @param key concrete parameter
     * @param date concrete date
     * @param force force update
     * @return value
     */
    public Double findValue(String key, String date, boolean force) {
        buildValues(force);
        ValueFinderVisitor valueFinderVisitor = new ValueFinderVisitor(date);
        boolean doit = false;
        for (ParamValues paramValues : paramValuesList) {
            if(paramValues.key.equals(key)){
                paramValues.accept(valueFinderVisitor);
                doit = true;
            }
        }
        if(!doit){
            System.out.println("No such parameter for given station");
            throw new IllegalArgumentException();
        }
        return valueFinderVisitor.value;
    }
    /**
     * Finding average
     *
     * @param key concrete parameter
     * @param startDate concrete startdate
     * @param endDate concrete startdate
     * @param force force update
     * @return value
     */
    public Double findAverageValue(String key, String startDate, String endDate, boolean force) {
        buildValues(force);
        AverageFinderVisitor averageFinderVisitor = new AverageFinderVisitor(startDate, endDate);
        boolean doit = false;
        for (ParamValues paramValues : paramValuesList) {
            if(paramValues.key.equals(key)){
                paramValues.accept(averageFinderVisitor);
                doit = true;
            }
        }
        if(!doit) {
            System.out.println("No such parameter for given station");
            throw new IllegalArgumentException();
        }
        return averageFinderVisitor.value;
    }

    /**
     * Finding min
     *
     * @param date concrete date
     * @param station concrete station
     * @param force force update
     * @return value
     */
    public ValueAtDateVisitor findMinValueAtDate(String date, String station, boolean force) {
        buildValues(force);
        ValueAtDateVisitor result = null;
        for(ParamValues paramValues: paramValuesList){
            ValueAtDateVisitor valueAtDateVisitor = new ValueAtDateVisitor(date,station);
            valueAtDateVisitor.reset(paramValues.key);
            paramValues.accept(valueAtDateVisitor);
            if(result == null || result.value==null || (valueAtDateVisitor.value!=null && result.value > valueAtDateVisitor.value)) result = valueAtDateVisitor;
        }
        return result;
    }

    public TreeMap<Double, String> findParametersOverNorm(String date, boolean force) {
        buildValues(force);
        TreeMap<Double, String> result = new TreeMap<>();
        ParametersOverNormVisitor parametersOverNormVisitor = new ParametersOverNormVisitor(date);
        for (ParamValues paramValues : paramValuesList) {
            paramValues.accept(parametersOverNormVisitor);
            if (parametersOverNormVisitor.value != null) {
                result.put(parametersOverNormVisitor.value, paramValues.key);
                parametersOverNormVisitor.reset();
            }
        }
        return result;
    }

    public FluctuationsFinderVisitor findMaxFluctuations(String startDate, String stationname, boolean force) {
        buildValues(force);
        FluctuationsFinderVisitor maxFluctuation = null;
        for (ParamValues paramValues : paramValuesList) {
            FluctuationsFinderVisitor fluctuationsFinderVisitor =
                    new FluctuationsFinderVisitor(startDate, stationname);
            fluctuationsFinderVisitor.reset(paramValues.key);
            paramValues.accept(fluctuationsFinderVisitor);
            if(maxFluctuation == null || maxFluctuation.maxFluctuations == null ||
                    (fluctuationsFinderVisitor.maxFluctuations!=null && maxFluctuation.maxFluctuations < fluctuationsFinderVisitor.maxFluctuations))
                maxFluctuation = fluctuationsFinderVisitor;
            }
        return maxFluctuation;
    }

    public FindMaxValueVisitor findMaxValue(String param, boolean force) {
        buildValues(force);
        FindMaxValueVisitor findMaxValueVisitor = new FindMaxValueVisitor(this.stationData.stationName);
        for (ParamValues paramValues : paramValuesList) {
            if (paramValues.key.equals(param)) {
                paramValues.accept(findMaxValueVisitor);
                break;
            }
        }
        return findMaxValueVisitor;
    }

    public FindMinValueVisitor findMinValue(String param, boolean force) {
        if (force) this.buildParam(param);
        FindMinValueVisitor findMinValueVisitor = new FindMinValueVisitor(this.stationData.stationName);
        for (ParamValues paramValues : paramValuesList) {
            if (paramValues.key.equals(param)) {
                paramValues.accept(findMinValueVisitor);
                break;
            }
        }
        return findMinValueVisitor;
    }

    public Map<String, List<String>> printChart(String startDate, String endDate, String stationName, String key, Map<String, List<String>> result, boolean force) { {
            this.buildValues(force);
            ChartGetterVisitor chartGetterVisitor = new ChartGetterVisitor(startDate, endDate, stationName, result);
            for (ParamValues paramValues : paramValuesList) {
                if (paramValues.key.equals(key)) {
                    paramValues.accept(chartGetterVisitor);
                    break;
                }
            }
            return result;
        }
    }

    public void getByCity(List<String> result, String city){
        if(this.stationData.city.name.equals(city))
            result.add(this.stationData.stationName);
    }
}
