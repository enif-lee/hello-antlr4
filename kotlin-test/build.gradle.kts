import com.strumenta.antlrkotlin.gradleplugin.AntlrKotlinTask

plugins {
    kotlin("jvm") version "1.8.0"
    application
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

val antlrKotlinVersion = "b5135079b8"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

buildscript {
    repositories {
        maven("https://jitpack.io")
    }

    dependencies {
        // add the plugin to the classpath
        classpath("com.strumenta.antlr-kotlin:antlr-kotlin-gradle-plugin:b5135079b8")
    }
}

dependencies {
    implementation("com.strumenta.antlr-kotlin:antlr-kotlin-runtime-jvm:$antlrKotlinVersion")
    testImplementation(kotlin("test"))
}

val antlrOutputDirectory = "build/generated-src/antlr"
tasks {
    compileKotlin {
        dependsOn("generateKotlinGrammarSource")
    }

    register<AntlrKotlinTask>("generateKotlinGrammarSource") {
        antlrClasspath = configurations.detachedConfiguration(
            project.dependencies.create("com.strumenta.antlr-kotlin:antlr-kotlin-target:$antlrKotlinVersion"),
        )
        maxHeapSize = "64m"
        packageName = "io.eniflee.expression"
        arguments = listOf("-visitor", "-long-messages")
        source = project.objects
            .sourceDirectorySet("antlr", "antlr")
            .srcDir("src/main/antlr").apply {
                include("*.g4") // outputDirectory is required, put it into the build directory
            }
        outputDirectory = File(antlrOutputDirectory)
    }

    test {
        useJUnitPlatform()
    }
}

sourceSets {
    main {
        kotlin {
            srcDir(antlrOutputDirectory)
        }
    }
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
