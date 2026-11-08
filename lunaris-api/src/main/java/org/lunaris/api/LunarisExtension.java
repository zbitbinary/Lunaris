package org.lunaris.api;

/**
 * Entry point implemented by extensions loaded by Lunaris.
 */
public interface LunarisExtension {
    default void onLoad() {
    }

    default void onEnable() {
    }

    default void onDisable() {
    }
}
