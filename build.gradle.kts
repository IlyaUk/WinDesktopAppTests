import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jnaVersion: String by project

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.java.dev.jna:jna:$jnaVersion")
    implementation("net.java.dev.jna:jna-platform:$jnaVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}