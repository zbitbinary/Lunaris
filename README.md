# Lunaris

**A custom Minecraft Bedrock Edition server implementation written in Java.**

Lunaris is an independent server project inspired by the modular philosophy of Minestom.
It is not based on PocketMine, Nukkit, Dragonfly, or another existing server implementation.

## Current status

Phase 1 — project foundation.

This repository currently contains the Gradle multi-project structure, module boundaries,
a minimal server bootstrap, and a properties-based configuration loader. It is **not yet
a playable Bedrock server** and does not accept Minecraft connections.

## Requirements

- JDK 25
- Gradle 9.2.1 or newer (or generate the wrapper using an installed Gradle)
- Windows, Linux, or macOS

Check Java:

```bash
java -version
```

## Build

From the repository root:

```bash
gradle build
```

To create a Gradle wrapper in this project:

```bash
gradle wrapper --gradle-version 9.2.1
```

Then use `gradlew.bat` on Windows or `./gradlew` on Linux/macOS.

## Run

```bash
gradle :lunaris-server:run
```

The server reads `config/lunaris.properties` by default.

You can pass a different config path as the application argument:

```bash
gradle :lunaris-server:run --args="config/lunaris.properties"
```

## Modules

| Module | Responsibility |
|---|---|
| `lunaris-common` | Shared configuration, lifecycle and utility types |
| `lunaris-protocol` | Bedrock protocol version metadata and future packet codecs |
| `lunaris-network` | Transport abstractions, sessions and connection lifecycle |
| `lunaris-world` | World, chunk, block and dimension model |
| `lunaris-entity` | Entity and player model |
| `lunaris-api` | Public extension and event API |
| `lunaris-plugin` | Plugin discovery and lifecycle |
| `lunaris-game` | Minigame instances, teams and game states |
| `lunaris-server` | Executable application and composition root |

## Networking direction

The target is the latest stable Bedrock release, not Preview. At project creation time,
the official protocol changelog lists Bedrock 1.26.62 as stable with protocol 2225.

The modern networking design is NetherNet-first. NetherNet uses WebRTC data channels and
an HTTPS signaling exchange. The networking module deliberately starts with transport
interfaces only: the actual WebRTC transport, signaling, identity validation, encryption,
and packet framing will be implemented and tested in a later phase. No fake or incomplete
network listener is started in this foundation milestone.

Official protocol references:

- https://mojang.github.io/bedrock-protocol-docs/changelog/
- https://mojang.github.io/bedrock-protocol-docs/guides/nether-net-onboarding-guide/

## Design rules

1. No dependency on an existing Minecraft server implementation.
2. Keep protocol, transport, gameplay and public API concerns separated.
3. Keep the supported Bedrock version explicit and updateable.
4. Do not claim client compatibility until login and gameplay flows are verified against a real client.
5. Prefer small, testable components over a monolithic server class.
