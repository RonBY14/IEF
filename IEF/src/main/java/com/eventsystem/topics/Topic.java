package com.eventsystem.topics;

import com.eventsystem.subscriber.Subscriber;

import java.util.HashSet;
import java.util.Set;

/**
 * Subscribers that express interest in a topic
 * can subscribe to it from the EventBus interface.
 *
 * @author Ron
 * @since 1.0
 */
public final class Topic {

    // Topics name
    private final String name;

    // Subscribers list the topic
    private final Set<Subscriber> subscribers;

    public Topic(String name) {
        if (name == null)
            throw new NullPointerException();
        this.name = name;

        subscribers = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }
}
