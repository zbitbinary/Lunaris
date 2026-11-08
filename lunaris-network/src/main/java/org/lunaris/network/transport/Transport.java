package org.lunaris.network.transport;

/**
 * Boundary for a Bedrock transport implementation.
 *
 * The first implementation will be NetherNet. This interface intentionally
 * contains no RakNet assumptions so the protocol/game layers remain transport-neutral.
 */
public interface Transport extends AutoCloseable {
    void start() throws Exception;

    boolean isRunning();

    @Override
    void close() throws Exception;
}
