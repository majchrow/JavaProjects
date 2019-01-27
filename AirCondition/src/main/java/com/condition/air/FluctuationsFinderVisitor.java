package com.condition.air;

import com.condition.air.Store.Subclasses.Value;


/**
 * Concrete FindMinValueVisitor implementing Visitor interface. Search for fluctuations.
 *
 * @author Dawid Majchrowski
 */

public class FluctuationsFinderVisitor implements Visitor {
    public String startDate;
    public String key;
    public String stationName;
    public Double maxFluctuations;

    public FluctuationsFinderVisitor(String startDate, String stationName) {
        this.startDate = startDate;
        this.stationName = stationName;
        this.key = null;
        this.maxFluctuations = null;
    }

    public void reset(String key){
        this.key = key;
        maxFluctuations = null;
    }

    @Override
    public void visit(ParamValues paramValues) {
        boolean ifCheck = false;
        for (Value value : paramValues.valueList) {
            if (value.date.equals(startDate)) {
                ifCheck = true;
                break;
            }
        }
        if (ifCheck) {
            Double max = Double.MIN_VALUE;
            Double min = Double.MAX_VALUE;
            for (Value value : paramValues.valueList) {
                if(value.value!=null){
                    if(value.value > max) max = value.value;
                    if(value.value < min) min = value.value;
                }
                if (value.date.equals(startDate)) {
                    break;
                }
            }
            if(max!=Double.MIN_VALUE && min != Double.MAX_VALUE)
                maxFluctuations = max - min;


        }

    }
}
