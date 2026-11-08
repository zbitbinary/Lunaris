package org.lunaris.entity;

import java.util.UUID;

/**
 * Base identity for server-side entities. Movement and metadata are added later.
 */
public abstract class Entity {
    private final UUID id = UUID.randomUUID();

    public final UUID id() {
        return id;
    }
}
