// Standalone build for Minecraft 26.x (unobfuscated) on Fabric.
// Kept separate from the root multi-loader Unimined build because 26.x ships without
// obfuscation, so there is no intermediary namespace and mixins target Mojang names directly.
pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/")
		gradlePluginPortal()
		mavenCentral()
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version("1.0.0")
}

rootProject.name = "zume-fabric26"
