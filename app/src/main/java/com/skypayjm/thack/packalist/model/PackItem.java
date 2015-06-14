package com.skypayjm.thack.packalist.model;

/**
 * Created by user on 6/13/2015.
 */
public class PackItem {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    private String name;
    private int quantity;
    private String uom;

    public PackItem() {
    }
}
