
package org.lunaris.common.lifecycle;

public final class ServerLifecycle {

    private ServerState state = ServerState.CREATED;

    public synchronized void transition(ServerState newState) {

        if (state == ServerState.STOPPED ||
            state == ServerState.FAILED) {

            throw new IllegalStateException(
                    "Cannot transition from " + state
            );
        }

        state = newState;
    }

    public synchronized ServerState getState() {
        return state;
    }

    public synchronized boolean isRunning() {
        return state == ServerState.RUNNING;
    }

    public synchronized boolean isStopping() {
        return state == ServerState.STOPPING;
    }
}
