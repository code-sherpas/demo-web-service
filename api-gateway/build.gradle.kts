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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.auth0:jwks-rsa:0.22.1")
    implementation(project(":contexts:authentication:core"))

    testFixturesImplementation(platform("io.arrow-kt:arrow-stack:1.2.1"))
    testFixturesImplementation("io.arrow-kt:arrow-core")
}
