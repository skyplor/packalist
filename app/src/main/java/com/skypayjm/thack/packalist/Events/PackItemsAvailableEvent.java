package com.skypayjm.thack.packalist.Events;

import com.skypayjm.thack.packalist.model.PackItem;

import java.util.List;

/**
 * Created by user on 6/13/2015.
 */
public class PackItemsAvailableEvent {
    // the object being sent using the bus
    private List<PackItem> pItems;

    public PackItemsAvailableEvent(List<PackItem> pItems) {
        this.pItems = pItems;
    }

    /**
     * @return the list of PackItems
     */
    public List<PackItem> getPItems() {
        return pItems;
    }
}
