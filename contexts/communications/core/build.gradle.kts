import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("kotlin-library-conventions")
    id("org.springframework.boot") version "3.1.5" apply false
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("plugin.spring") version "1.9.10"
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

archivesName = "communications.core"

dependencies {
    implementation("org.springframework:spring-beans")
    implementation("jakarta.inject:jakarta.inject-api")
    implementation(project(":libraries:ok-http"))
    implementation("com.google.code.gson:gson")
    implementation(project(":libraries:jsonserialization"))
    testImplementation("com.google.code.gson:gson")
}
