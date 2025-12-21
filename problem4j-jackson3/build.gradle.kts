plugins {
    id("internal.convention-java-library")
    id("internal.convention-publishing")
    alias(libs.plugins.nmcp)
}

java {
    toolchain.languageVersion =
        providers.gradleProperty("internal.jackson3.java.version").map { JavaLanguageVersion.of(it) }
}

dependencies {
    // Main
    api(libs.problem4j.core)

    compileOnly(libs.jackson3.databind)
    compileOnly(libs.jackson3.dataformat.xml)

    // Test
    testImplementation(libs.jackson3.databind)
    testImplementation(libs.jackson3.dataformat.xml)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

// see buildSrc/src/main/kotlin/internal.convention-publishing.gradle.kts
internalPublishing {
    displayName = "Problem4J Jackson3"
    description = "Jackson3 integration for library implementing RFC7807"
}

nmcp {
    publishAllPublicationsToCentralPortal {
        username = System.getenv("PUBLISHING_USERNAME")
        password = System.getenv("PUBLISHING_PASSWORD")

        publishingType = "USER_MANAGED"
    }
}
