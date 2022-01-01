package com.eventsystem.utils;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

/**
 * Application's common interface for runnable.
 *
 * @author Ron
 * @since 1.0
 */
public interface ThreadControl extends Runnable {

    /**
     * @see #start(String)
     */
    boolean start();

    /**
     * Creates a new thread and
     * starts it only if the thread
     * is null or dead.
     *
     * @param name      custom name for the thread.
     * @return          true if the thread successfully started.
     */
    boolean start(String name);

    /**
     * A thread should terminate naturally, so
     * avoid thread-interruption when possible
     * in order to avoid errors.
     *
     * @param interrupt - should interrupt the thread or not.
     */
    boolean terminate(boolean interrupt);

    /**
     * Used for cleaning/closing resources. This helps to avoid memory leaks.
     *
     * @param closeables - instances that are using memory and must be closed.
     */
    void clean(@NotNull Closeable @NotNull... closeables);
}
