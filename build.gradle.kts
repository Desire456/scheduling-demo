plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.tbank"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    maven {
        url = uri("https://artifactory.tcsbank.ru/artifactory/maven-all/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.3")

    // https://mvnrepository.com/artifact/org.jobrunr/jobrunr-spring-boot-3-starter
    implementation("org.jobrunr:jobrunr-spring-boot-3-starter:7.4.1")
    // https://mvnrepository.com/artifact/org.jobrunr/jobrunr-kotlin-support
    implementation("org.jobrunr:jobrunr-kotlin-support:3.1.2")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-quartz
    implementation("org.springframework.boot:spring-boot-starter-quartz")

    implementation("com.github.kagkarlsson:db-scheduler-spring-boot-starter:15.2.0")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.5")

    // https://mvnrepository.com/artifact/io.dropwizard/dropwizard-core
    implementation("io.dropwizard:dropwizard-core:4.0.12")
    // https://mvnrepository.com/artifact/io.micrometer/micrometer-core
    implementation("io.micrometer:micrometer-core:1.14.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
