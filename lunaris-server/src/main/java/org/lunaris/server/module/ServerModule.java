
package org.lunaris.server.module;

public interface ServerModule {

    String getName();

    default void initialize() throws Exception {
    }

    default void shutdown() throws Exception {
    }
}
