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
    api("io.github.malczuuu.problem4j:problem4j-core:1.1.0")

    compileOnly("com.fasterxml.jackson.core:jackson-databind:2.20.0")

    // Test
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
