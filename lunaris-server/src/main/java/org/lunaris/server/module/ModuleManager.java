
package org.lunaris.server.module;

import org.lunaris.common.logging.LunarisLogger;

import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {

    private final LunarisLogger logger =
            new LunarisLogger("ModuleManager");

    private final List<ServerModule> modules =
            new ArrayList<>();

    private final List<ServerModule> initializedModules =
            new ArrayList<>();

    private boolean initialized;

    public void register(ServerModule module) {
        if (initialized) {
            throw new IllegalStateException(
                    "Cannot register modules after initialization"
            );
        }

        if (module == null) {
            throw new IllegalArgumentException(
                    "Module cannot be null"
            );
        }

        boolean duplicate = modules.stream()
                .anyMatch(existing ->
                        existing.getName().equalsIgnoreCase(
                                module.getName()
                        )
                );

        if (duplicate) {
            throw new IllegalArgumentException(
                    "A module named " + module.getName()
                            + " is already registered"
            );
        }

        modules.add(module);
    }

    public void initialize() throws Exception {
        if (initialized) {
            throw new IllegalStateException(
                    "Modules have already been initialized"
            );
        }

        initialized = true;

        try {
            for (ServerModule module : modules) {
                logger.info("Initializing " + module.getName());

                module.initialize();
                initializedModules.add(module);
            }
        } catch (Exception exception) {
            logger.error(
                    "Module initialization failed. Rolling back."
            );

            shutdown();
            throw exception;
        }
    }

    public void shutdown() {
        for (int i = initializedModules.size() - 1; i >= 0; i--) {
            ServerModule module = initializedModules.get(i);

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

        initializedModules.clear();
    }
}
