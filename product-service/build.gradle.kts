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

val springCloudVersion = "4.2.2"
val resilience4jVersion = "3.3.0"
val flywayVersion = "11.15.0"
val hibernateValidatorVersion = "9.0.1.Final"
val mapstructVersion = "1.6.3"
val feignMicrometerVersion = "13.6"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-logging")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:${springCloudVersion}")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:${resilience4jVersion}")

    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation ("io.opentelemetry:opentelemetry-exporter-otlp")
    implementation("io.micrometer:micrometer-registry-otlp")
    implementation("io.github.openfeign:feign-micrometer:${feignMicrometerVersion}")

    implementation("org.flywaydb:flyway-database-postgresql:${flywayVersion}")
    implementation("org.flywaydb:flyway-core:${flywayVersion}")

    implementation("org.hibernate.validator:hibernate-validator:${hibernateValidatorVersion}")
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
