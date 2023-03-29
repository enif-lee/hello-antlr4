plugins {
    kotlin("jvm") version "1.8.0"
    application
    antlr
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
//            srcDirs("build/generated-src/antlr")
        }
    }
}

dependencies {
    // https://mvnrepository.com/artifact/org.antlr/antlr4-runtime
    implementation("org.antlr:antlr4-runtime:4.12.0")
    antlr("org.antlr:antlr4:4.12.0")

    testImplementation(kotlin("test"))
}

tasks {
    generateGrammarSource {
        arguments = arguments + listOf("-visitor", "-long-messages")
    }

    compileKotlin {
        dependsOn("generateGrammarSource")
    }

    test {
        useJUnitPlatform()
    }
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
