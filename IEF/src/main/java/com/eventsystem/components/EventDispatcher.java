package com.eventsystem.components;

import com.eventsystem.events.Event;
import com.eventsystem.utils.RunnableService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Dispatches events and uses its own single thread
 * to call a polymorphic callback method of a recipient.
 *
 * @author Ron
 * @since 1.0
 */
public class EventDispatcher extends RunnableService {

    private final BlockingQueue<Event> events;

    public EventDispatcher() {
        events = new LinkedBlockingQueue<>();

        start();
    }

    /**
     * Pushes a single event to dispatch into the queue.
     *
     * @param event     the event to dispatch.
     * @return          true if the event successfully queued.
     */
    public boolean push(Event event) {
        return events.offer(event);
    }

    /**
     * Takes no event out of the queue (When available)
     * and performs a callback to the polymorphic method
     * of a specific recipient that handles the event.
     */
    private synchronized void dispatch() {
        Event event;

        try {
            event = events.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        event.getRecipient().handle(event);
    }

    @Override
    public void run() {
        while (!terminated)
            dispatch();
    }
}
