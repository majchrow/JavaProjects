package com.condition.air;

import com.condition.air.Store.Subclasses.Value;

/**
 * Concrete AverageFinderVisitor implementing Visitor interface. Search for average value for given param.
 *
 * @author Dawid Majchrowski
 */

public class AverageFinderVisitor implements Visitor {
    public String startDate;
    public String endDate;
    public Double value;

    public AverageFinderVisitor(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.value = null;
    }

    @Override
    public void visit(ParamValues paramValues) {
        Integer counter = 0;
        Double sum = 0.;
        boolean start = false;
        boolean end = false;
        for (Value value : paramValues.valueList) {

            if (!start) {
                if (value.date.equals(endDate)) {
                    start = true;
                    if (value.value != null) {
                        counter++;
                        sum += value.value;
                    }
                }
            } else {
                if (value.value != null) {
                    counter++;
                    sum += value.value;
                }
                if (value.date.equals(startDate)) {
                    end = true;
                    break;
                }

            }
        }
        if (!start)
        {
            System.out.println("Didn't find end date");
            throw new IllegalArgumentException();
        }
        if (!end)
        {
            System.out.println("Didn't find start date");
            throw new IllegalArgumentException();
        }
        if(counter == 0){
            System.out.println("All values for given period was null");
            throw new IllegalArgumentException();
        }
            value = sum / counter;
    }
}
