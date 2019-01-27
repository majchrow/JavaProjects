package com.condition.air;

import com.condition.air.Store.Subclasses.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Concrete ChartGetterVisitor implementing Visitor interface. Print Chart from given Data and stations names.
 *
 * @author Dawid Majchrowski
 */

public class ChartGetterVisitor implements Visitor {
    String startDate;
    String endDate;
    String stationName;
    Map<String, List<String>> result;

    public ChartGetterVisitor(String startDate, String endDate,String stationName, Map<String, List<String>> result) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.stationName = stationName;
        this.result = result;
    }

    @Override
    public void visit(ParamValues paramValues) {
        boolean start = false;
        boolean end = false;
        for (Value value : paramValues.valueList) {
            if (!start) {
                if (value.date.equals(startDate)) {
                    start = true;
                    if (value.value!=null)
                        this.buildChart(value);
                }
            }
            else {
                if(value.value!=null)
                    this.buildChart(value);
                if(value.date.equals(endDate)){
                    end = true;
                    break;
                }
            }
        }
        if(!end){
            System.err.println("Wrong date was given");
            throw new IllegalArgumentException();
        }
    }
    public void buildChart(Value value){
        String chart = this.convert(value.value, stationName);
        if(this.result.get(value.date)==null){
            this.result.put(value.date,new LinkedList<String>());
        }
        List<String> toAdd = this.result.get(value.date);;
        toAdd.add(0, chart);
    }

    public String convert(Double value, String stationName){
        StringBuilder chart = new StringBuilder(String.format("%-30s","("+stationName+") "));
        Integer count = (int) Math.floor(value);
        for(int i=0;i<count;++i)
            chart.append("\u2588");
        chart.append(" " + value);
        return chart.toString();
    }



}

