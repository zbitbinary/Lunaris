package org.lunaris.server;

import org.lunaris.common.config.ServerConfig;
import org.lunaris.protocol.BedrockProtocol;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LunarisMain {
    private static final Logger LOGGER = Logger.getLogger("Lunaris");

    private LunarisMain() {
    }

    public static void main(String[] args) {
        Path configPath = args.length > 0
                ? Path.of(args[0])
                : Path.of("config", "lunaris.properties");

        final ServerConfig config;
        try {
            config = ServerConfig.load(configPath);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Could not load configuration: " + configPath, exception);
            System.exit(1);
            return;
        }

        LunarisServer server = new LunarisServer(config);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown, "lunaris-shutdown"));
        server.start();
    }
}
