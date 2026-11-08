
package org.lunaris.server;

import org.lunaris.common.config.ServerConfig;
import org.lunaris.common.lifecycle.ServerLifecycle;
import org.lunaris.common.lifecycle.ServerState;
import org.lunaris.common.logging.LunarisLogger;
import org.lunaris.common.scheduler.ServerScheduler;
import org.lunaris.server.module.ModuleManager;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public final class LunarisServer {

    private final LunarisLogger logger =
            new LunarisLogger("Lunaris");

    private final ServerConfig config;
    private final ServerLifecycle lifecycle;
    private final ServerScheduler scheduler;
    private final ModuleManager moduleManager;

    private final CountDownLatch shutdownLatch =
            new CountDownLatch(1);

    private final AtomicBoolean shutdownStarted =
            new AtomicBoolean(false);

    public LunarisServer(ServerConfig config) {
        this.config = config;
        this.lifecycle = new ServerLifecycle();
        this.scheduler = new ServerScheduler();
        this.moduleManager = new ModuleManager();
    }

    public void start() {
        try {
            lifecycle.transition(ServerState.STARTING);

            logger.info("Starting Lunaris Server...");
            logger.info("Initializing server components...");

            moduleManager.initialize();

            lifecycle.transition(ServerState.RUNNING);

            logger.info("Lunaris is now running.");
            logger.info("Waiting for connections...");

            Runtime.getRuntime().addShutdownHook(
                    new Thread(this::stop, "Lunaris-Shutdown")
            );

        } catch (Exception exception) {
            logger.error(
                    "Failed to start Lunaris: " + exception.getMessage()
            );

            lifecycle.transition(ServerState.FAILED);
            cleanup();

            shutdownLatch.countDown();
        }
    }

    public void stop() {
        if (!shutdownStarted.compareAndSet(false, true)) {
            return;
        }

        ServerState currentState = lifecycle.getState();

        if (currentState == ServerState.STOPPED) {
            shutdownLatch.countDown();
            return;
        }

        if (currentState == ServerState.RUNNING
                || currentState == ServerState.STARTING) {
            lifecycle.transition(ServerState.STOPPING);
        }

        logger.info("Stopping Lunaris Server...");

        cleanup();

        if (lifecycle.getState() == ServerState.STOPPING) {
            lifecycle.transition(ServerState.STOPPED);
        }

        logger.info("Lunaris Server stopped.");

        shutdownLatch.countDown();
    }

    private void cleanup() {
        logger.info("Stopping scheduler...");
        scheduler.shutdown();

        logger.info("Shutting down modules...");
        moduleManager.shutdown();
    }

    public void awaitShutdown() throws InterruptedException {
        shutdownLatch.await();
    }

    public ServerState getState() {
        return lifecycle.getState();
    }

    public ServerScheduler getScheduler() {
        return scheduler;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
