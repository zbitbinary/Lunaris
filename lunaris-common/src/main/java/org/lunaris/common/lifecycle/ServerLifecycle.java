
package org.lunaris.common.lifecycle;

public final class ServerLifecycle {

    private ServerState state = ServerState.CREATED;

    public synchronized void transition(ServerState newState) {
        boolean valid = switch (state) {
            case CREATED -> newState == ServerState.STARTING
                    || newState == ServerState.FAILED;

            case STARTING -> newState == ServerState.RUNNING
                    || newState == ServerState.STOPPING
                    || newState == ServerState.FAILED;

            case RUNNING -> newState == ServerState.STOPPING
                    || newState == ServerState.FAILED;

            case STOPPING -> newState == ServerState.STOPPED
                    || newState == ServerState.FAILED;

            case STOPPED, FAILED -> false;
        };

        if (!valid) {
            throw new IllegalStateException(
                    "Invalid server state transition: "
                            + state + " -> " + newState
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
