package org.lunaris.protocol;

/**
 * The stable Bedrock target is kept in one place so protocol upgrades are explicit.
 * Update this only after checking the official Mojang protocol changelog.
 */
public final class BedrockProtocol {
    public static final String GAME_VERSION = "1.26.62";
    public static final int PROTOCOL_VERSION = 2225;

    private BedrockProtocol() {
    }
}
