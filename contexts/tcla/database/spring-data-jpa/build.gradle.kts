plugins {
    id("kotlin-library-conventions")
    id("org.springframework.boot") version "3.1.5" apply false
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.10"
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin"){
        because("required by Spring")
    }
    implementation("org.jetbrains.kotlin:kotlin-reflect"){
        because("required by Spring")
    }

    implementation("jakarta.inject:jakarta.inject-api")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    implementation("com.google.code.gson:gson")

    implementation(project(":contexts:tcla:core"))

    implementation(project(":contexts:accounts:core"))

    implementation(project(":libraries:search"))
    implementation(project(":libraries:time"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.postgresql:postgresql")
    testImplementation(testFixtures(project(":contexts:tcla:core")))
}

