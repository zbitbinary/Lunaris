
package org.lunaris.server.module;

import org.lunaris.common.logging.LunarisLogger;

import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {

    private final LunarisLogger logger =
            new LunarisLogger("ModuleManager");

    private final List<ServerModule> modules =
            new ArrayList<>();

    public void register(ServerModule module) {
        modules.add(module);
    }

    public void initialize() throws Exception {

        for (ServerModule module : modules) {

            logger.info("Initializing " + module.getName());

            module.initialize();
        }
    }

    public void shutdown() {

        for (int i = modules.size() - 1; i >= 0; i--) {

            ServerModule module = modules.get(i);

            try {
                logger.info("Shutting down " + module.getName());
                module.shutdown();

            } catch (Exception exception) {

                logger.error(
                        "Failed to shut down " + module.getName()
                                + ": " + exception.getMessage()
                );
            }
        }
    }
}
