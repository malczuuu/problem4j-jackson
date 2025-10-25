/*
 * This module targets Java 8 for its main sources to maintain compatibility with older runtime environments used by
 * dependent systems.
 *
 * Unit tests, however, are executed on Java 17 because JUnit 6 requires Java 17 or newer. The Gradle toolchain
 * configuration ensures that only the test compilation and execution use Java 17, while the main code remains compiled
 * for Java 8.
 *
 * In short:
 *   - src/main -> Java 8 (for compatibility)
 *   - src/test -> Java 17 (required by JUnit 6)
 */

plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
    id("com.gradleup.nmcp").version("1.2.0")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(8)
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    // Main
    api(libs.problem4j.core)

    compileOnly(libs.jackson2.databind)

    // Test
    testImplementation(libs.jackson2.databind)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])

            pom {
                name = project.name
                description = "Jackson integration for library implementing RFC7807"
                url = "https://github.com/malczuuu/${rootProject.name}"
                inceptionYear = "2025"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://opensource.org/licenses/MIT"
                    }
                }
                developers {
                    developer {
                        id = "malczuuu"
                        name = "Damian Malczewski"
                        url = "https://github.com/malczuuu"
                    }
                }
                issueManagement {
                    system = "GitHub Issues"
                    url = "https://github.com/malczuuu/${rootProject.name}/issues"
                }
                scm {
                    connection = "scm:git:https://github.com/malczuuu/${rootProject.name}.git"
                    developerConnection = "scm:git:git@github.com:malczuuu/${rootProject.name}.git"
                    url = "https://github.com/malczuuu/${rootProject.name}"
                }
            }
        }
    }
}

nmcp {
    publishAllPublicationsToCentralPortal {
        username = System.getenv("PUBLISHING_USERNAME")
        password = System.getenv("PUBLISHING_PASSWORD")

        publishingType = "USER_MANAGED"
    }
}

signing {
    if (project.hasProperty("sign")) {
        useInMemoryPgpKeys(
            System.getenv("SIGNING_KEY"),
            System.getenv("SIGNING_PASSWORD")
        )
        sign(publishing.publications["maven"])
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
}

/**
 * Disable doclint to avoid errors and warnings on missing JavaDoc comments.
 */
tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).apply {
        addStringOption("Xdoclint:none", "-quiet")
    }
}

/**
 * JUnit 6 requires at least Java 17 to run tests.
 */
tasks.named<JavaCompile>("compileTestJava") {
    javaCompiler.set(
        javaToolchains.compilerFor {
            languageVersion = JavaLanguageVersion.of(17)
        }
    )
}

/**
 * JUnit 6 requires at least Java 17 to run tests.
 */
tasks.withType<Test>().configureEach {
    javaLauncher.set(
        javaToolchains.launcherFor {
            languageVersion = JavaLanguageVersion.of(17)
        }
    )
    useJUnitPlatform()
}
