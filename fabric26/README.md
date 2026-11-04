# Zume for Minecraft 26.x (Fabric)

Standalone build of Zume for unobfuscated Minecraft (26.1+), targeting **26.3**.

Minecraft 26.x ships without obfuscation, so Fabric no longer uses intermediary names and the root
Unimined build (which remaps everything through intermediary and JVM-downgrades to Java 8) can't target it.
This build uses the non-remapping Fabric Loom and Mojang names directly.

What is shared: the whole platform-independent core in `../api` (zoom logic, easing, config + hot reload,
lang files) is compiled in unchanged. Only the Minecraft-facing glue is new:

| Feature | Where |
| --- | --- |
| Zoom / Zoom In / Zoom Out keybinds (Z, =, -) | `ZumeKeyBind`, `Fabric26Zume` |
| Smooth FOV zoom | `GameRendererMixin` (`render`, easing) + `CameraMixin` (`calculateFov`) |
| Scroll-to-zoom (and in spectator) | `MouseHandlerMixin` (`onScroll`) |
| Cinematic camera + sensitivity floor while zoomed | `MouseHandlerMixin` (`turnPlayer`) |
| Third-person zoom distance | `CameraMixin` (`alignWithEntity` → `getMaxZoom`) |
| Mod Menu "Config" button opens `zume.json5` | `ZumeModMenuIntegration` |

Not carried over: the Embeddium options page (Embeddium does not exist for 26.x).

## Building

Requires Java 25 (Gradle's toolchain resolver downloads it if missing).

```
cd fabric26
./gradlew build
```

The jar lands in `fabric26/build/libs/`. Version numbers live in `fabric26/gradle.properties`.
