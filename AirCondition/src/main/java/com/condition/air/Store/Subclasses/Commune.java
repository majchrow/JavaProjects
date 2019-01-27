package com.condition.air.Store.Subclasses;

/**
 * Class which stores CommuneData.
 *
 * @author Dawid Majchrowski
 */
public class Commune {
    String communeName;
    String districtName;
    String provinceName;
    public Commune() {
    }

    @Override
    public String toString() {
        return communeName+districtName+provinceName;
    }
}
