import org.gradle.api.JavaVersion.VERSION_21

plugins {
    id("java")
    id("application")
}

group = "org.comparator"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val javaVersion = VERSION_21.majorVersion.toInt()
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.5.18")
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}


application {
    mainClass = "org.comparator.Main"
}

tasks.test {
    useJUnitPlatform()
}