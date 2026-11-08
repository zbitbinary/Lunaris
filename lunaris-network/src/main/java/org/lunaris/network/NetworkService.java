package org.lunaris.network;

import org.lunaris.network.transport.Transport;

/**
 * Owns the selected transport. A concrete transport is registered in a later phase.
 */
public final class NetworkService implements AutoCloseable {
    private Transport transport;

    public void install(Transport transport) {
        if (this.transport != null) {
            throw new IllegalStateException("A transport is already installed.");
        }
        this.transport = transport;
    }

    public boolean hasTransport() {
        return transport != null;
    }

    public void start() throws Exception {
        if (transport == null) {
            throw new IllegalStateException("No transport implementation has been installed yet.");
        }
        transport.start();
    }

    @Override
    public void close() throws Exception {
        if (transport != null) {
            transport.close();
        }
    }
}
