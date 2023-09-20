plugins {
    id("java")
}

group = "org.jetbrains.research.ictil"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":various"))
}

tasks.test {
    useJUnitPlatform()
}