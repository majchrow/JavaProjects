package com.condition.air;

import java.util.HashMap;
import java.util.Map;

/**
 * Norm class that stores norm values for all parameters.
 *
 * @author Dawid Majchrowski
 */
public class Norm {
    public static Map<String, Double> parameterValue;

    static{
        parameterValue = new HashMap<>();
        parameterValue.put("PM10",50.);
        parameterValue.put("PM2.5",25.);
        parameterValue.put("NO2",200.);
        parameterValue.put("O3", Double.MAX_VALUE);
        parameterValue.put("SO2",350.);
        parameterValue.put("C6H6",5.);
        parameterValue.put("CO",10000.);
    }
}
