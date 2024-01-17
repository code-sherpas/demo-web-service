buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm")
    id("org.owasp.dependencycheck") version "8.4.2"
}

repositories {
    mavenCentral()
}

dependencyCheck {
    failBuildOnCVSS = 11F
}

val dependencyCheckAggregateTask = tasks.named("dependencyCheckAggregate")

val dependencyCheckUpdateTask = tasks.named("dependencyCheckUpdate")

dependencyCheckAggregateTask {
    dependsOn(dependencyCheckUpdateTask)
}

// Overriding of versions of dependencies managed by Spring Boot.
//ext["snakeyaml.version"] = "2.2"
