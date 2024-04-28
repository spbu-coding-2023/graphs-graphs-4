plugins {
	kotlin("jvm") version "1.9.23"
	id("io.gitlab.arturbosch.detekt").version("1.23.6")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(kotlin("test"))
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	jvmToolchain(21)
}

detekt {
	toolVersion = "1.23.6"

	source.setFrom("src/main/kotlin")

	config.setFrom("config/detekt/detekt.yml")
	buildUponDefaultConfig = true
	allRules = false

	ignoreFailures = false
}