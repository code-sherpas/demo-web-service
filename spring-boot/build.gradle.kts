import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin-application-conventions")
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.10"
//    id("io.sentry.jvm.gradle") version "4.1.0"
}

/*sentry {
//    debug.set(true)
    includeSourceContext.set(true)

    org.set("demo")
    projectName.set("tcla-web-service")
    authToken.set(System.getenv("SENTRY_AUTH_TOKEN"))
}*/

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") {
        because("it is required by Spring")
    }
    implementation("org.jetbrains.kotlin:kotlin-reflect") {
        because("it is required by Spring")
    }

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.jobrunr:jobrunr-spring-boot-3-starter:6.3.2")

    compileOnly("org.springframework.boot:spring-boot-devtools")

    implementation(project(":api-gateway"))
    implementation(project(":message-broker"))
    implementation(project(":contexts:authentication:core"))
    implementation(project(":contexts:tcla:core"))
    implementation(project(":contexts:tcla:web-api:spring-web"))
    implementation(project(":contexts:tcla:database:spring-data-jpa"))
    implementation(project(":contexts:accounts:core"))
    implementation(project(":contexts:accounts:web-api:spring-web"))
    implementation(project(":contexts:accounts:auth0"))
    implementation(project(":contexts:communications:core"))
    implementation(project(":contexts:analytics"))

    implementation(project(":libraries:ok-http"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
    }
}
