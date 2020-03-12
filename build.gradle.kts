plugins {
    kotlin("jvm") version "1.3.61"
    id("maven-publish")
    id("com.gradle.plugin-publish").version("0.10.1")
    publishing
}

group = "kr.heartpattern.spikot"
version = "1.0.0"

repositories {
    maven("https://maven.heartpattern.kr/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(gradleKotlinDsl())
    implementation(kotlin("gradle-plugin"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

pluginBundle {
    website = "https://github.com/Spikot/MangroveGradle"
    vcsUrl = "https://github.com/Spikot/MangroveGradle"
    description = "Gradle plugin for Mangrove"
    plugins {
        create("Mangrove") {
            id = "kr.heartpattern.spikot.mangrove"
            displayName = "MangroveGradle"
        }
    }
}