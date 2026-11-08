package org.lunaris.world;

/**
 * Placeholder for the custom world model. Chunk storage and ticking arrive in later phases.
 */
public final class World {
    private final String name;

    public World(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("World name cannot be blank.");
        }
        this.name = name;
    }

    public String name() {
        return name;
    }
}
