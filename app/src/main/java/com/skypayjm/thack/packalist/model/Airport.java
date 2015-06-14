package com.skypayjm.thack.packalist.model;

/**
 * Created by user on 6/13/2015.
 */
public class Airport {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private String value;
    private String label;

    @Override
    public String toString() {
        return label;
    }

    public Airport() {

    }
}
