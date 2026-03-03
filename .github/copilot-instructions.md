# Copilot Instructions - `problem4j-jackson`

Jackson integration modules for [problem4j-core](https://github.com/problem4j/problem4j-core) - serialization and
deserialization of RFC 7807 Problem objects.

## Modules

| Module               | Jackson | Java target | Entry point                                                                                                          |
|----------------------|---------|-------------|----------------------------------------------------------------------------------------------------------------------|
| `problem4j-jackson2` | 2.x     | 8           | [`ProblemModule`](../problem4j-jackson2/src/main/java/io/github/problem4j/jackson2/ProblemModule.java)               |
| `problem4j-jackson3` | 3.x     | 17          | [`ProblemJacksonModule`](../problem4j-jackson3/src/main/java/io/github/problem4j/jackson3/ProblemJacksonModule.java) |

Prioritize changes in the submodule matching the Jackson version in context.

## Build & Validate

Requires **JDK 17+**. Dependencies managed in `gradle/libs.versions.toml`. Custom Gradle plugins live in `buildSrc`.

```shell
./gradlew                  # default: spotlessApply build (preferred)
./gradlew spotlessApply    # auto-format code
./gradlew build            # compile + test + spotlessCheck
./gradlew test             # tests only
```

## Agent Rules

- Do not use terminal commands (e.g., `cat`, `find`, `ls`) to read or list project files - use IDE/agent tools instead.

## Coding Rules

- No self-explaining comments - only add comments for non-obvious context.
- No wildcard imports.
- Follow existing code patterns and naming conventions.
- Let `spotlessApply` handle all formatting - never format manually.

## Test Conventions

- Method naming: `givenThis_whenThat_thenWhat`.
- No `// given`, `// when`, `// then` section comments.
- Cover both positive and negative cases.
- Use AssertJ for assertions.