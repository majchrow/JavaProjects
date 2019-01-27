package com.condition.air.Store.Subclasses;

/**
 * Class which stores CityData.
 *
 * @author Dawid Majchrowski
 */
public class City {
    public int id;
    public String name;
    public Commune commune;
    public City() {
    }

    @Override
    public String toString() {
        return String.valueOf(id) + commune + name;
    }
}
