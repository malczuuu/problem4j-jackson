import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("internal.common-convention")
    id("java-library")
}

//
// Not assigning java.toolchain.languageVersion as submodules have different Java version requirements.
//

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
    options.encoding = "UTF-8"
}

tasks.withType<Jar>().configureEach {
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
        attributes["Build-Jdk-Spec"] = java.toolchain.languageVersion.get().toString()
        attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
    }
    from("${rootProject.rootDir}/LICENSE") {
        into("META-INF/")
        rename { "LICENSE.txt" }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
        exceptionFormat = TestExceptionFormat.SHORT
        showStandardStreams = true
    }

    systemProperty("user.language", "en")
    systemProperty("user.country", "US")
}

// buildSrc/src/main/kotlin/internal.common-convention.gradle.kts - "printVersion" task definition
tasks.withType<PublishToMavenLocal>().configureEach {
    finalizedBy("printVersion")
}
