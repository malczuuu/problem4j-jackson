plugins {
    id("internal.java-library-convention")
    id("internal.publishing-convention")
    alias(libs.plugins.nmcp)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

dependencies {
    // Main
    api(libs.jspecify)
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

// see buildSrc/src/main/kotlin/internal.publishing-convention.gradle.kts
internalPublishing {
    displayName = "Problem4J Jackson3"
    description = "Jackson3 integration for library implementing RFC7807 (aka RFC9457)."
}
