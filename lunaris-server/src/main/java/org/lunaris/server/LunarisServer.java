package org.lunaris.server;

import org.lunaris.common.config.ServerConfig;
import org.lunaris.network.NetworkService;
import org.lunaris.protocol.BedrockProtocol;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public final class LunarisServer {
    private static final Logger LOGGER = Logger.getLogger("Lunaris");

    private final ServerConfig config;
    private final NetworkService network = new NetworkService();
    private final AtomicBoolean stopped = new AtomicBoolean(false);

    public LunarisServer(ServerConfig config) {
        this.config = config;
    }

    public void start() {
        LOGGER.info("Starting Lunaris " + LunarisBuild.VERSION);
        LOGGER.info("Target Bedrock: " + BedrockProtocol.GAME_VERSION
                + " (protocol " + BedrockProtocol.PROTOCOL_VERSION + ")");
        LOGGER.info("Server name: " + config.name());
        LOGGER.info("Configured bind address: " + config.host() + ":" + config.port());
        LOGGER.info("Configured transport: " + config.transport());
        LOGGER.info("Maximum players: " + config.maxPlayers());

        // Networking is deliberately not started until a real NetherNet transport exists.
        LOGGER.info("Foundation startup complete. No Bedrock listener is active yet.");
        LOGGER.info("Press Ctrl+C to stop Lunaris.");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            shutdown();
        }
    }

    public void shutdown() {
        if (!stopped.compareAndSet(false, true)) {
            return;
        }

        LOGGER.info("Stopping Lunaris...");
        try {
            network.close();
        } catch (Exception exception) {
            LOGGER.warning("Network shutdown reported an issue: " + exception.getMessage());
        }
        LOGGER.info("Lunaris stopped.");
    }
}
