package com.skypayjm.thack.packalist.Events;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;

import de.greenrobot.event.EventBus;

/**
 * Singleton that holds the app-wide eventbus
 */
@EBean(scope = Scope.Singleton)
public class BusProvider {
    private EventBus eventBus;

    /**
     * Lazy load the event bus
     */
    public synchronized EventBus getEventBus() {
        if (eventBus == null) {
            eventBus = new EventBus();
        }

        return eventBus;
    }
}
