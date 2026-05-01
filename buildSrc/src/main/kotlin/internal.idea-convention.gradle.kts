import org.jetbrains.gradle.ext.Gradle
import org.jetbrains.gradle.ext.JUnit
import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings

plugins {
    id("org.jetbrains.gradle.plugin.idea-ext")
}

idea {
    project {
        settings {
            runConfigurations {
                create<Gradle>("Clean [problem4j-jackson]") {
                    taskNames = listOf("clean")
                    projectPath = rootProject.rootDir.absolutePath
                }
                create<Gradle>("Build [problem4j-jackson]") {
                    taskNames = listOf("spotlessApply build")
                    projectPath = rootProject.rootDir.absolutePath
                }
                create<Gradle>("Format Code [problem4j-jackson]") {
                    taskNames = listOf("spotlessApply")
                    projectPath = rootProject.rootDir.absolutePath
                }
                create<JUnit>("JUnit [problem4j-jackson2]") {
                    moduleName = "problem4j-jackson.problem4j-jackson2.test"
                    workingDirectory = rootProject.rootDir.absolutePath
                    packageName = "io.github.problem4j.jackson2"
                }
                create<JUnit>("JUnit [problem4j-jackson3]") {
                    moduleName = "problem4j-jackson.problem4j-jackson3.test"
                    workingDirectory = rootProject.rootDir.absolutePath
                    packageName = "io.github.problem4j.jackson3"
                }
            }
        }
    }
}
