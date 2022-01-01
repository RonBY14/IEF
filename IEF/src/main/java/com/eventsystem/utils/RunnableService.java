package com.eventsystem.utils;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;

/**
 * Application's common implementation of ThreadControl.
 *
 * @author Ron
 * @since 1.0
 */
public abstract class RunnableService implements ThreadControl {

    protected Thread thread;
    protected boolean terminated;

    public RunnableService() {}

    public boolean isTerminated() {
        return terminated;
    }

    @Override
    public boolean start() {
        return start(null);
    }

    @Override
    public boolean start(String name) {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this, name != null ? name : this.getClass().getSimpleName());
            terminated = false;
            thread.start();
            return true;
        }
        return false;
    }

    @Override
    public boolean terminate(boolean shouldInterrupt) {
        if (thread != null && thread.isAlive()) {
            terminated = true;

            if (shouldInterrupt)
                thread.interrupt();
            return true;
        }
        return false;
    }

    @Override
    public void clean(@NotNull Closeable @NotNull... closeables) {
        try {
            for (Closeable closeable : closeables) closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
