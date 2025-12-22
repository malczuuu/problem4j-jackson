pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}

rootProject.name = "problem4j-jackson"

include(":problem4j-jackson2")
include(":problem4j-jackson3")
