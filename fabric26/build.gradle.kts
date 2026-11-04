plugins {
	// Non-remapping Loom variant used for unobfuscated Minecraft (26.1+)
	id("net.fabricmc.fabric-loom") // version set in settings.gradle.kts (loom_version)
}

operator fun String.invoke(): String = providers.gradleProperty(this).get()

group = "dev.nolij"
version = "${"mod_version"()}+mc${"minecraft_version"()}"
base.archivesName = "zume-fabric"

java {
	toolchain.languageVersion = JavaLanguageVersion.of(25)
}

repositories {
	maven("https://maven.taumc.org/releases") // LibNolij
	maven("https://maven.blamejared.com") // ZSON
	maven("https://maven.terraformersmc.com/releases/") // Mod Menu
	mavenCentral()
}

// Zume's platform-independent core (zoom logic, config, file watcher) is reused verbatim from :api.
sourceSets.main {
	java.srcDir("../api/src/main/java")
	java.srcDir("src/generated/java")
	resources.srcDir("../api/src/main/resources")
}

dependencies {
	minecraft("com.mojang:minecraft:${"minecraft_version"()}")
	implementation("net.fabricmc:fabric-loader:${"loader_version"()}")
	implementation("net.fabricmc.fabric-api:fabric-api:${"fabric_api_version"()}")
	
	compileOnly("org.jetbrains:annotations:26.0.2")
	
	// ../api uses Manifold's unchecked checked-exceptions, same as the root build
	compileOnly("systems.manifold:manifold-rt:${"manifold_version"()}")
	annotationProcessor("systems.manifold:manifold-exceptions:${"manifold_version"()}")
	compileOnly("com.terraformersmc:modmenu:${"modmenu_version"()}")
	
	implementation("dev.nolij:zson:${"zson_version"()}")
	include("dev.nolij:zson:${"zson_version"()}")
	implementation("dev.nolij:libnolij:${"libnolij_version"()}")
	include("dev.nolij:libnolij:${"libnolij_version"()}")
}

tasks.processResources {
	exclude("**/*.xcf", "**/*.lang")
	val props = mapOf(
		"mod_version" to project.version.toString(),
		"minecraft_version" to "minecraft_version"(),
		"loader_version" to "loader_version"(),
	)
	inputs.properties(props)
	filesMatching("fabric.mod.json") { expand(props) }
}

tasks.withType<JavaCompile>().configureEach {
	options.encoding = "UTF-8"
	options.release = 25
	options.compilerArgs.add("-Xplugin:Manifold no-bootstrap")
}
