plugins {
    idea
    java
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "org.homecorporation"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.3.0")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.flywaydb:flyway-core:11.15.0")
    implementation("org.hibernate.validator:hibernate-validator:9.0.1.Final")
    implementation("org.mapstruct:mapstruct:1.6.3")
    runtimeOnly("io.micrometer:micrometer-registry-otlp")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
