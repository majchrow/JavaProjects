package com.condition.air;

import com.condition.air.Store.Data.ValuesData;
import com.condition.air.Store.Subclasses.Value;

import java.util.List;

/**
 * Class that reprezents concrete parameter.
 *
 * @author Dawid Majchrowski
 */


public class ParamValues implements Visitable {
        public String key;
        public List<Value> valueList;

    public ParamValues(ValuesData valuesData) {
        key = valuesData.key;
        valueList = valuesData.values;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
