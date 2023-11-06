plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "study.spring.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // logging interface
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    // logging interface implementation
    implementation("ch.qos.logback:logback-classic:1.4.11")


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}