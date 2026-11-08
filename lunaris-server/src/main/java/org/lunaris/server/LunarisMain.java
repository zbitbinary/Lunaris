
package org.lunaris.server;

import org.lunaris.common.config.ServerConfig;

import java.nio.file.Path;

public final class LunarisMain {

    private LunarisMain() {
    }

    public static void main(String[] args) {
        try {
            ServerConfig config = ServerConfig.load(
                    Path.of("config/lunaris.properties")
            );

            LunarisServer server = new LunarisServer(config);

            server.start();
            server.awaitShutdown();

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();

        } catch (Exception exception) {
            System.err.println(
                    "[Lunaris] Fatal error: " + exception.getMessage()
            );

            exception.printStackTrace();
        }
    }
}
