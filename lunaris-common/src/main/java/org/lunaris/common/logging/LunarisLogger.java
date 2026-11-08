
package org.lunaris.common.logging;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class LunarisLogger {

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String name;

    public LunarisLogger(String name) {
        this.name = name;
    }

    public void info(String message) {
        log("INFO", message);
    }

    public void warn(String message) {
        log("WARN", message);
    }

    public void error(String message) {
        log("ERROR", message);
    }

    public void debug(String message) {
        log("DEBUG", message);
    }

    private void log(String level, String message) {

        String time = LocalTime.now().format(TIME_FORMAT);

        System.out.printf(
                "[%s] [%s] [%s] %s%n",
                time,
                name,
                level,
                message
        );
    }
}
