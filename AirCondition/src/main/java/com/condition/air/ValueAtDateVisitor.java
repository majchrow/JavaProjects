package com.condition.air;

import com.condition.air.Store.Subclasses.Value;


/**
 * Concrete ValueAtDateVisitor implementing Visitor interface. Search for value at Given Date.
 *
 * @author Dawid Majchrowski
 */
public class ValueAtDateVisitor implements Visitor {
    public String date;
    public String station;
    public String key;
    public Double value;

    public ValueAtDateVisitor(String date, String station) {
        this.date = date;
        this.station = station;
        this.key = null;
        value = null;
    }

    public void reset(String key) {
        this.key = key;
        value = null;
    }

    @Override
    public void visit(ParamValues paramValues) {
        for (Value value : paramValues.valueList) {
            if (value.date.equals(date))
                this.value = value.value;
        }
    }
}
