package com.eventsystem.events;

import com.eventsystem.subscriber.Subscriber;

/**
 * Every event must extend this class.
 *
 * @author Ron
 * @since 1.0
 */
public abstract class Event implements Cloneable {

    protected Subscriber recipient;

    protected String topicChannel;

    public Event() {}

    public Subscriber getRecipient() {
        return recipient;
    }

    public void setRecipient(Subscriber recipient) {
        this.recipient = recipient;
    }

    public String getTopicChannel() {
        return topicChannel;
    }

    public void setTopicChannel(String topicChannel) {
        this.topicChannel = topicChannel;
    }

    @Override
    public Event clone() {
        try {
            return (Event) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
