
val postgresVersion = "42.3.8"
val telegramBotVersion = "6.8.0"

plugins {
    id("org.flywaydb.flyway") version("9.21.1")
    id("org.springframework.boot") version "3.1.3"
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.jpa") version "1.9.0"
    application
}

apply(plugin = "io.spring.dependency-management")

group = "financial.app"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven ("https://repo.gradle.org/gradle/libs-releases")
}

val flywayMigration: Configuration = configurations.create("flywayMigration")

flyway {
    validateOnMigrate = false
    configurations = arrayOf("flywayMigration")
    url = "jdbc:postgresql://localhost:5432/alta-cresta"
    user = "postgres"
    password = "postgres"
    cleanDisabled = false
}

dependencies {
    flywayMigration("org.postgresql:postgresql:$postgresVersion")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.hibernate.javax.persistence:hibernate-jpa-2.0-api:1.0.0.Final")
    implementation ("org.hibernate.orm:hibernate-core:6.0.2.Final")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")

    implementation("org.flywaydb:flyway-core:9.21.2")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.data:spring-data-jpa")

    implementation("org.telegram:telegrambots:$telegramBotVersion")
    implementation("org.telegram:telegrambotsextensions:$telegramBotVersion")
    implementation("org.telegram:telegrambots-spring-boot-starter:$telegramBotVersion")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

tasks.clean {
    delete("src/main/java")
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "ApplicationKt")
    }
}

application {
    mainClass.set("ApplicationKt")
}