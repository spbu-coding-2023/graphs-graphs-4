import io.gitlab.arturbosch.detekt.Detekt

plugins {
	kotlin("jvm") version "1.9.23"
	id("io.gitlab.arturbosch.detekt").version("1.23.6")
	jacoco
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(kotlin("test"))
}

kotlin {
	jvmToolchain(21)
}

detekt {
	toolVersion = "1.23.6"

	source.setFrom("$projectDir/src/main/kotlin", "$projectDir/src/test/kotlin")

	config.setFrom("$projectDir/config/detekt/detekt.yml")
	buildUponDefaultConfig = true
	allRules = false

	ignoreFailures = false

	basePath = rootProject.projectDir.absolutePath
}

jacoco {
	toolVersion = "0.8.12"
	reportsDirectory = layout.buildDirectory.dir("reports/jacoco")
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<Detekt>().configureEach {
	reports {
		html.required.set(true) // observe findings in your browser with structure and code snippets
		sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
	}
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required = false
		csv.required = true
		html.required = true
	}
}