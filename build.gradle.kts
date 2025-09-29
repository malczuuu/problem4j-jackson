import com.diffplug.spotless.LineEnding

plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
    id("com.diffplug.spotless").version("8.0.0")
    id("com.gradleup.nmcp.aggregation").version("1.2.0")
}

group = "io.github.malczuuu.problem4j"

/**
 * In order to avoid hardcoding snapshot versions, we derive the version from the current Git commit hash. For CI/CD add
 * -Pversion={releaseVersion} parameter to match Git tag.
 */
version =
    if (version == "unspecified")
        getSnapshotVersion(rootProject.rootDir)
    else
        version

java {
    toolchain.languageVersion = JavaLanguageVersion.of(8)
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    // Main
    api("io.github.malczuuu.problem4j:problem4j-core:1.0.0")
    compileOnly("com.fasterxml.jackson.core:jackson-databind:2.19.2")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))

    // Test
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
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
                url = "https://github.com/malczuuu/${project.name}"
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
                    url = "https://github.com/malczuuu/${project.name}/issues"
                }
                scm {
                    connection = "scm:git:git://github.com/malczuuu/${project.name}.git"
                    developerConnection = "scm:git:git@github.com:malczuuu/${project.name}.git"
                    url = "https://github.com/malczuuu/${project.name}"
                }
            }
        }
    }
}

nmcpAggregation {
    centralPortal {
        username = System.getenv("PUBLISHING_USERNAME")
        password = System.getenv("PUBLISHING_PASSWORD")

        publishingType = "USER_MANAGED"
    }
    publishAllProjectsProbablyBreakingProjectIsolation()
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

spotless {
    format("misc") {
        target("**/*.gradle.kts", "**/.gitattributes", "**/.gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    java {
        target("src/**/*.java")

        googleJavaFormat("1.28.0")
        forbidWildcardImports()
        lineEndings = LineEnding.UNIX
    }
}

tasks.register("printVersion") {
    doLast {
        println("Project version: $version")
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
