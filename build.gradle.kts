import com.diffplug.spotless.LineEnding

plugins {
    `java-library`
    `maven-publish`
    id("com.diffplug.spotless") version "7.2.1"
}

group = "com.github.malczuuu"

if (version == "unspecified") {
    version = Versioning.getSnapshotVersion(rootProject.rootDir)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io/") }
}

val jacksonDatabindVersion = "2.19.2"
val problem4jCoreVersion = "3.2.0-rc1"
val junitJupiterVersion = "5.13.4"
val junitPlatformVersion = "1.13.4"

dependencies {
    api("com.fasterxml.jackson.core:jackson-databind:${jacksonDatabindVersion}")

    api("com.github.malczuuu:problem4j-core:${problem4jCoreVersion}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${junitJupiterVersion}")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:${junitPlatformVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])

            pom {
                name.set(project.name)
                description.set("Jackson module for library implementing RFC7807")
                url.set("https://github.com/malczuuu/${project.name}")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
            }
        }
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
