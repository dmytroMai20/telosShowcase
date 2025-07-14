plugins {
    java
    kotlin("jvm") version "2.1.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.dmytromai"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib"))
}

tasks{
    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles() // Important for plugin.yml merging if any
    }
    build { dependsOn(shadowJar) }
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}