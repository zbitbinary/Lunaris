
package org.lunaris.common.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class ServerScheduler {

    private final ScheduledExecutorService executor =
            Executors.newScheduledThreadPool(2, runnable -> {
                Thread thread = new Thread(
                        runnable,
                        "Lunaris-Scheduler"
                );

                thread.setDaemon(true);
                return thread;
            });

    public void runTask(Runnable task) {
        executor.execute(task);
    }

    public void runLater(
            Runnable task,
            long delay,
            TimeUnit unit
    ) {
        executor.schedule(task, delay, unit);
    }

    public void runRepeating(
            Runnable task,
            long initialDelay,
            long period,
            TimeUnit unit
    ) {
        executor.scheduleAtFixedRate(
                task,
                initialDelay,
                period,
                unit
        );
    }

    public void shutdown() {
        executor.shutdown();

        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException exception) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
