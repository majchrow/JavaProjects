package com.condition.air;

import com.condition.air.Store.Subclasses.Value;


/**
 * Concrete FindMinValueVisitor implementing Visitor interface. Search for min value.
 *
 * @author Dawid Majchrowski
 */

public class FindMinValueVisitor implements Visitor{


    String name;
    Double value;
    String date;

    public FindMinValueVisitor(String name) {
        this.name = name;
        value = null;
        date = null;
    }

    @Override
    public void visit(ParamValues paramValues) {
        Double minValue = Double.MAX_VALUE;
        String tmpDate = null;
        for (Value value : paramValues.valueList) {
            if(value.value!=null){
                if(value.value<minValue){
                    minValue = value.value;
                    tmpDate = value.date;
                }
            }
        }
        value = minValue;
        date = tmpDate;
//        System.out.println(name + " " + String.valueOf(value));
    }
}
