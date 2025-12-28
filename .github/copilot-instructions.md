# Copilot Coding Agent Onboarding Guide for `problem4j-jackson`

## Project Details

- **Repository:** `problem4j-jackson`.
- **Purpose:** Jackson integration modules for [problem4j-core](https://github.com/problem4j/problem4j-core), enabling
  seamless serialization and deserialization of RFC 7807 Problem objects. Contains:
    - `problem4j-jackson2`: For Jackson 2.x (Java 8 baseline),
    - `problem4j-jackson3`: For Jackson 3.x (Java 17 baseline).
- **Languages:** Java (main), Kotlin (build scripts).
- **Frameworks/Tools:** Gradle (Kotlin DSL), Jackson (v2 & v3) JUnit Jupiter, AssertJ, Spotless, GitHub Actions CI.
- **Modules:**
    - `problem4j-jackson2` - Jackson 2.x integration,
    - `problem4j-jackson3` - Jackson 3.x integration.
- **Java Version:** JVM (Java 8 for Jackson 2.x, Java 17 for Jackson 3.x, JDK 17 for build/CI due to Gradle 9+ runtime
  requirements).
- **Repo Size:** Small (two modules, core source, tests, build scripts, CI/CD workflows).

## Build, Test, Lint, and Validation Steps

- **Bootstrap:** No special bootstrap steps required. All dependencies resolved via Gradle.
- **Build:**
    - Run `./gradlew build` from the repository root (or `gradlew.bat build` on Windows).
    - Java 17+ required (for Gradle; code for `problem4j-jackson2`, Jackson 2.x integration is compiled to Java 8
      bytecode).
- **Test:**
    - Tests run automatically with `./gradlew build` or separately via `./gradlew test`.
    - Test files in `src/test/java` under each module.
    - Tests for `problem4j-jackson2` run on Java 17, even though code targets Java 8 bytecode (for JUnit 6).
- **Lint:**
    - Spotless check runs automatically on build. To manually lint/fix, use `./gradlew spotlessApply`.
    - Run `./gradlew spotlessCheck` to validate code style.
    - Run `./gradlew spotlessApply` to auto-format code.
    - Lint config in `build.gradle.kts`.
    - For limiting failures and noise, prefer running `./gradlew spotlessApply build` instead of just `./gradlew build`.
- **Clean:**
    - Run `./gradlew clean` to remove build artifacts.
- **Validation:**
    - CI/CD via GitHub Actions:
        - `.github/workflows/gradle-build.yml` (build/test),
        - `.github/workflows/gradle-dependency-submission.yml` (dependency graph),
        - `.github/workflows/gradle-publish-release.yml` (release).
    - All CI builds use JDK 17 and Gradle Wrapper.

## Project Layout & Key Files

- **Root Files:** `build.gradle.kts`, `settings.gradle.kts`, `README.md`, `RELEASING.md`, `gradlew`, `gradlew.bat`,
  `gradle/libs.versions.toml`, `.github/workflows/`.
- **Source Code:** `src/main/java` in each module.
- **Tests:** `src/test/java` in each module.
- **Build Scripts:** All modules have `build.gradle.kts`.
- **Build Utils:** Custom Gradle scripts in `buildSrc`.
- **Module Directories:**
    - `problem4j-jackson2`: Jackson 2.x integration,
    - `problem4j-jackson3`: Jackson 3.x integration.
- **Jackson entry points**
    - Jackson 2.x: [`ProblemModule`](../problem4j-jackson2/src/main/java/io/github/problem4j/jackson2/ProblemModule.java),
    - Jackson 3.x: [`ProblemJacksonModule`](../problem4j-jackson3/src/main/java/io/github/problem4j/jackson3/ProblemJacksonModule.java).

## Coding Guidelines

- Do not add self-explaining comments. Use comments only for clarity/context.
- Do not use wildcard imports.
- Always rely on `spotlessApply` task from Gradle for code formatting.
- Follow existing code patterns and naming conventions.
- Use Gradle tasks for build, test, and lint. Do not attempt manual compilation or test running.

## Unit Test Guidelines

- Name test methods using: `givenThis_whenThat_thenWhat`.
- Do not use comments like `// given`, `// when`, or `// then` to mark test sections; structure should be clear from the
  method body.
- Test both positive and negative cases for each feature or behavior.
- Prefer fluent assertion libraries such as AssertJ or Hamcrest.

## Agent Instructions

- Trust these instructions for build, test, lint, and validation steps. Only search the codebase if information here is
  incomplete or incorrect.
- Prioritize changes in submodules matching the Jackson version in use.
- Always validate changes with a full build and test run before considering the task complete.

## Troubleshooting & Workarounds

- If build fails due to Java version, ensure Java 17+ is installed and selected.
- If Spotless fails, run `./gradlew spotlessApply` to auto-fix formatting.
- For dependency issues, check `gradle/libs.versions.toml` and run `./gradlew --refresh-dependencies`.
- For Windows, use `gradlew.bat` or Git Bash for shell compatibility with scripts.

## Additional Notes / Quick Reference

- Always run `./gradlew build` before pushing changes.
- Ensure all tests pass locally and that Spotless does not report errors.
- For publishing, set required environment variables and use the documented Gradle tasks.
- Check CI status on GitHub after pushing/PR.
- For further details, see `README.md` and `build.gradle.kts`. For CI/CD specifics, review workflow YAMLs in
  `.github/workflows/`.
