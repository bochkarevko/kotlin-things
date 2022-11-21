plugins {
    kotlin("multiplatform") version "1.7.20" apply false
    id("org.jetbrains.kotlinx.benchmark") version "0.4.4"
    application
}

group = "org.jetbrains"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
    }
}