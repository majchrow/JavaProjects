package com.condition.air;

import com.condition.air.Store.Subclasses.Value;


/**
 * Concrete FindMaxValueVisitor implementing Visitor interface. Search for max value.
 *
 * @author Dawid Majchrowski
 */
public class FindMaxValueVisitor implements  Visitor{

    String name;
    Double value;
    String date;

    public FindMaxValueVisitor(String name) {
        this.name = name;
        value = null;
        date = null;
    }

    @Override
    public void visit(ParamValues paramValues) {
        Double maxValue = Double.MIN_VALUE;
        String tmpDate = null;
        for (Value value : paramValues.valueList) {
            if(value.value!=null){
                if(value.value>maxValue){
                    maxValue = value.value;
                    tmpDate = value.date;
                }
            }
        }
        value = maxValue;
        date = tmpDate;
//        System.out.println(name + " " + String.valueOf(value));
    }
}
