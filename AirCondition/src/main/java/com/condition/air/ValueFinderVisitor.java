package com.condition.air;

import com.condition.air.Store.Subclasses.Value;

/**
 * Concrete ValueFinderVisitor implementing Visitor interface. Search for concrete value.
 *
 * @author Dawid Majchrowski
 */
public class ValueFinderVisitor implements Visitor {
    public String date;
    public Double value;

    public ValueFinderVisitor(String date) {
        this.date = date;
        value = null;
    }

    @Override
    public void visit(ParamValues paramValues) {
        boolean flag = false;
        for (Value value : paramValues.valueList) {
            if (value.date.equals(date)){
                this.value = value.value;
                flag = true;
            }
        }
        if(!flag){
            System.out.println("Wrong date given");
            throw new IllegalArgumentException();
        }
    }

}
