package com.eventsystem.exceptions;

/**
 * Throw when a requested topic does not exit.
 *
 * @author Ron
 * @since 1.0
 */
public class NoSuchTopicException extends RuntimeException {

    public NoSuchTopicException(String topic) {
        super(topic);
    }
}
