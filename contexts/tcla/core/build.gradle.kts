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

archivesName = "tcla.core"

dependencies {
    implementation("jakarta.inject:jakarta.inject-api")
    implementation(project(":libraries:transactional"))
    implementation(project(":libraries:uuid-validation"))
    implementation(project(":libraries:search"))
    implementation(project(":libraries:logging"))

    implementation(project(":contexts:authentication:core"))
    implementation(project(":contexts:accounts:core"))
    implementation(project(":contexts:communications:core"))

    api(project(":libraries:time"))
    implementation("org.jobrunr:jobrunr-spring-boot-3-starter")

    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("io.cucumber:cucumber-java8:7.14.0")
    testImplementation("io.cucumber:cucumber-java:7.14.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.14.0")

    testFixturesImplementation(platform("io.arrow-kt:arrow-stack:1.2.1"))
    testFixturesImplementation("io.arrow-kt:arrow-core")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("cucumber.execution.parallel.enabled", true)
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
    systemProperty("cucumber.publish.quiet", "true")
}
