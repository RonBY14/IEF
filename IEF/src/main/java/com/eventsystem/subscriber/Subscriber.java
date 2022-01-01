package com.eventsystem.subscriber;

import com.eventsystem.events.Event;

/**
 * In order to subscribe to topics, class must implement this interface.
 *
 * @author Ron
 * @since 1.0
 */
public interface Subscriber {

    /**
     * A callback polymorphic method for handling
     * the event for a specific subscriber.
     *
     * @param event - event passed by the framework's dispatcher.
     */
    void handle(Event event);
}
