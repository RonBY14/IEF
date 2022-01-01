package com.eventsystem.exceptions;

/**
 * Throw when topic name is not available for creation.
 *
 * @author Ron
 * @since 1.0
 */
public class TopicNameUnavailableException extends RuntimeException {

    public TopicNameUnavailableException(String name) {
        super(name);
    }
}
