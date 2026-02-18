plugins {
    id("internal.errorprone-convention")
    id("internal.java-library-convention")
    id("internal.publishing-convention")
    alias(libs.plugins.nmcp)
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

    errorprone(libs.errorprone.core)
    errorprone(libs.nullaway)
}

// see buildSrc/src/main/kotlin/internal.publishing-convention.gradle.kts
internalPublishing {
    displayName = "Problem4J Jackson3"
    description = "Jackson3 integration for library implementing RFC7807 (aka RFC9457)."
}

tasks.named<JavaCompile>("compileJava") {
    options.release = 17
}

tasks.withType<Javadoc>().configureEach {
    javadocTool = javaToolchains.javadocToolFor { languageVersion.set(JavaLanguageVersion.of(17)) }
}
