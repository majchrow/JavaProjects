package com.condition.air;

import com.condition.air.Store.Subclasses.Value;

/**
 * Concrete ParametersOverNormVisitor implementing Visitor interface. Search for fluctuations.
 *
 * @author Dawid Majchrowski
 */
public class ParametersOverNormVisitor implements Visitor {

    public String date;
    public Double value;
    public ParametersOverNormVisitor(String date){
        this.date = date;
    }

    public void reset(){
        this.value = null;
    }

    @Override
    public void visit(ParamValues paramValues) {
        for (Value value : paramValues.valueList) {
            if (value.date.equals(date)) {
                if (value.value != null)
                    if (value.value > Norm.parameterValue.get(paramValues.key))
                        this.value = value.value-Norm.parameterValue.get(paramValues.key);
                break;
            }
        }
    }
}
