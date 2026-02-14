plugins {
    id("internal.java-library-convention")
    id("internal.mrjar-module-info-convention")
    id("internal.publishing-convention")
    alias(libs.plugins.nmcp)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(8)
}

dependencies {
    // Main
    api(libs.jspecify)
    api(libs.problem4j.core)

    compileOnly(libs.jackson2.databind)
    compileOnly(libs.jackson2.dataformat.xml)

    // Test
    testImplementation(libs.jackson2.databind)
    testImplementation(libs.jackson2.dataformat.xml)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

// see buildSrc/src/main/kotlin/internal.publishing-convention.gradle.kts
internalPublishing {
    displayName = "Problem4J Jackson2"
    description = "Jackson2 integration for library implementing RFC7807 (aka RFC9457)."
}

// This module targets Java 8 for its main sources to maintain compatibility with older runtime environments used by
// dependent systems.
//
// Unit tests, however, are executed on Java 17 because JUnit 6 requires Java 17 or newer. The Gradle toolchain
// configuration ensures that only the test compilation and execution use Java 17, while the main code remains compiled
// for Java 8.
//
// In short:
//   - src/main -> Java 8 (for compatibility)
//   - src/test -> Java 17 (required by JUnit 6)

// JUnit 6 requires at Java 17+, main keeps Java 8.
tasks.named<JavaCompile>("compileTestJava") {
    javaCompiler = javaToolchains.compilerFor { languageVersion = JavaLanguageVersion.of(17) }
}

// JUnit 6 requires at Java 17+, main keeps Java 8.
tasks.withType<Test>().configureEach {
    javaLauncher = javaToolchains.launcherFor { languageVersion = JavaLanguageVersion.of(17) }
}
