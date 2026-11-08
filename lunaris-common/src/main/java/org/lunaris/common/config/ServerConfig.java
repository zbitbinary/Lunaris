package org.lunaris.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Immutable server configuration loaded from a Java properties file.
 */
public record ServerConfig(
        String name,
        String motd,
        String host,
        int port,
        int maxPlayers,
        String bedrockVersion,
        int bedrockProtocol,
        String transport
) {
    public static ServerConfig load(Path path) throws IOException {
        Properties properties = new Properties();

        if (Files.notExists(path)) {
            throw new IOException("Configuration file does not exist: " + path.toAbsolutePath());
        }

        try (InputStream input = Files.newInputStream(path)) {
            properties.load(input);
        }

        return new ServerConfig(
                properties.getProperty("server.name", "Lunaris"),
                properties.getProperty("server.motd", "A custom Bedrock server."),
                properties.getProperty("server.host", "0.0.0.0"),
                readInt(properties, "server.port", 19132),
                readInt(properties, "server.max-players", 100),
                properties.getProperty("bedrock.version", "1.26.62"),
                readInt(properties, "bedrock.protocol", 2225),
                properties.getProperty("network.transport", "nethernet")
        );
    }

    private static int readInt(Properties properties, String key, int fallback) throws IOException {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            return fallback;
        }

        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            throw new IOException("Invalid integer for '" + key + "': " + value, exception);
        }
    }
}
