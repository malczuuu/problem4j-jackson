plugins {
    id("internal.errorprone-convention")
    id("internal.jacoco-convention")
    id("internal.java-library-convention")
    id("internal.publishing-convention")
    alias(libs.plugins.nmcp)
}

dependencies {
    // Main
    compileOnly(libs.jspecify)
    compileOnly(libs.problem4j.core)
    compileOnly(libs.jackson3.databind)
    compileOnly(libs.jackson3.dataformat.xml)

    // Test
    testImplementation(libs.jspecify)
    testImplementation(libs.problem4j.core)
    testImplementation(libs.jackson3.databind)
    testImplementation(libs.jackson3.dataformat.xml)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)

    errorprone(libs.errorprone.core)
    errorprone(libs.nullaway)
}

// see build-logic/src/main/kotlin/internal.publishing-convention.gradle.kts
internalPublishing {
    displayName = "Problem4J Jackson3"
    description = "Jackson3 integration for library implementing RFC7807 (and RFC9457)"
}

tasks.named<JavaCompile>("compileJava").configure {
    options.release = 17
}

tasks.withType<Javadoc>().configureEach {
    javadocTool = javaToolchains.javadocToolFor { languageVersion = JavaLanguageVersion.of(17) }
}
