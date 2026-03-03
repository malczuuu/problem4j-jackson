# Problem4J Jackson

[![Build Status](https://github.com/problem4j/problem4j-jackson/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/problem4j/problem4j-jackson/actions/workflows/gradle-build.yml)
[![Sonatype](https://img.shields.io/maven-central/v/io.github.problem4j/problem4j-jackson2?label=problem4j-jackson2)][maven-central-jackson2]
[![Sonatype](https://img.shields.io/maven-central/v/io.github.problem4j/problem4j-jackson3?label=problem4j-jackson3)][maven-central-jackson3]
[![License](https://img.shields.io/github/license/problem4j/problem4j-jackson)](https://github.com/problem4j/problem4j-jackson/blob/main/LICENSE)

Jackson `2.x` and `3.x` integration module for [`problem4j-core`][problem4j-core]. Provides easy support for serializing
and deserializing the `Problem` model using [Jackson's `ObjectMapper`][jackson].

> Note that [RFC 7807][rfc7807] was later extended in [RFC 9457][rfc9457], however core concepts remain the same.

Project contains two submodules, `problem4j-jackson2` for Jackson `2.x` and `problem4j-jackson3` for Jackson `3.x`. Both
modules have the similar API and functionality, but are compiled against different versions of Jackson (and by extension
against different versions of Java). Choose the one that matches the version of Jackson you are using in your project.

| Module               | Jackson Version                                     | Java Baseline |
|----------------------|-----------------------------------------------------|---------------|
| `problem4j-jackson2` | `com.fasterxml.jackson.core:jackson-databind:2.x.y` | Java 8        |
| `problem4j-jackson3` | `tools.jackson.core:jackson-databind:3.x.y`         | Java 17       |

Instead of releasing version `2.0`, library was split into two modules, because `jackson-3.x` has different maven
`groupId` so it's technically possible to have both versions of `ObjectMapper` included in the same project.

## Table of Contents

- [Features](#features)
- [Example](#example)
    - [Example for `problem4j-jackson2` (Jackson `2.x`)](#example-for-problem4j-jackson2-jackson-2x)
    - [Example for `problem4j-jackson3` (Jackson `3.x`)](#example-for-problem4j-jackson3-jackson-3x)
- [Usage](#usage)
    - [Maven dependency for `problem4j-jackson2` (Jackson `2.x`)](#maven-dependency-for-problem4j-jackson2-jackson-2x)
    - [Maven dependency for `problem4j-jackson3` (Jackson `3.x`)](#maven-dependency-for-problem4j-jackson3-jackson-3x)
- [Project Status](#project-status)
- [Problem4J Links](#problem4j-links)
- [Building from source](#building-from-source)

## Features

- ✅ Seamless JSON serialization of `Problem` objects.
- ✅ Accurate deserialization into immutable `Problem` instances.
- ✅ Compatible with standard Jackson `ObjectMapper`.
- ✅ Pluggable via Jackson's `Module` system or predefined `MixIn` interface.
- ✅ Lightweight, with no external dependencies beyond Jackson and `problem4j-core`.
- ✅ Support for both Jackson2 (`com.fasterxml.jackson`) and Jackson3 (`tools.jackson`).
- ✅ Integrated with JSpecify annotations for nullability and Kotlin interop (since `v1.4.0`).
- ✅ Supports **Java Platform Module System** (since `v1.4.0`). Artifact for `problem4j-jackson2` uses multi-release JAR
  to support Java 8 and Java 9+ module system, while artifact for `problem4j-jackson3` is compiled with Java 17 and
  supports module system out of the box.

## Example

Examples differ, depending on baseline **Jackson** version.

### Example for `problem4j-jackson2` (Jackson `2.x`)

Serialize and deserialize object using `ObjectMapper` from **Jackson `2.x`**.

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.problem4j.core.Problem;
import io.github.problem4j.jackson2.ProblemModule;

ObjectMapper mapper = new ObjectMapper().registerModule(new ProblemModule());

Problem problem = 
    Problem.builder()
        .title("Bad Request")
        .status(400)
        .detail("not a valid json")
        .build();

String json = mapper.writeValueAsString(problem);
Problem parsed = mapper.readValue(json, Problem.class);
```

Module is included in [`com.fasterxml.jackson.databind.Module`][com.fasterxml.jackson.databind.Module] for automatic
service discovery. Registration can also be done with `findAndRegisterModules()` method or by adding a `ProblemMixIn`.

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.problem4j.core.Problem;
import io.github.problem4j.jackson2.ProblemMixIn;

ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
ObjectMapper mapper = new ObjectMapper().addMixIn(Problem.class, ProblemMixIn.class);
```

If using Java module system, add the following `requires` directive to your `module-info.java` file.

```java
module org.example.project {
    requires io.github.problem4j.jackson2;
}
```

### Example for `problem4j-jackson3` (Jackson `3.x`)

Serialize and deserialize object using `JsonMapper` from **Jackson `3.x`**.

```java
import io.github.problem4j.core.Problem;
import io.github.problem4j.jackson3.ProblemJacksonModule;
import tools.jackson.databind.json.JsonMapper;

JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

Problem problem = 
    Problem.builder()
        .title("Bad Request")
        .status(400)
        .detail("not a valid json")
        .build();

String json = mapper.writeValueAsString(problem);
Problem parsed = mapper.readValue(json, Problem.class);
```

Module is included in [`tools.jackson.databind.JacksonModule`][tools.jackson.databind.JacksonModule] for automatic
service discovery. Registration can also be done with `findAndAddModules()` method or by adding a `ProblemJacksonMixIn`.

```java
import io.github.problem4j.core.Problem;
import io.github.problem4j.jackson3.ProblemJacksonMixIn;
import tools.jackson.databind.json.JsonMapper;

JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();
JsonMapper mapper = JsonMapper.builder().addMixIn(Problem.class, ProblemJacksonMixIn.class).build();
```

If using Java module system, add the following `requires` directive to your `module-info.java` file.

```java
module org.example.project {
    requires io.github.problem4j.jackson3;
}
```

## Usage

Add library as dependency to Maven or Gradle. **Java 8** or higher is required to use `problem4j-jackson2` library.
**Java 17** or higher is required to use `problem4j-jackson3` library.

The `problem4j-jackson` modules does **not** declare `jackson-databind` as a transitive dependency. You should add
`jackson-databind` explicitly as your main Jackson dependency.

### Maven dependency for `problem4j-jackson2` (Jackson `2.x`)

[![Sonatype](https://img.shields.io/maven-central/v/io.github.problem4j/problem4j-jackson2?label=problem4j-jackson2)][maven-central-jackson2]

1. Maven:
   ```xml
   <dependencies>
       <dependency>
           <groupId>com.fasterxml.jackson.core</groupId>
           <artifactId>jackson-databind</artifactId>
           <version>2.21.0</version>
       </dependency>
       <dependency>
           <groupId>io.github.problem4j</groupId>
           <artifactId>problem4j-jackson2</artifactId>
           <version>{version}</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Groovy or Kotlin DSL):
   ```groovy
   dependencies {
       implementation("com.fasterxml.jackson.core:jackson-databind:2.21.0")
       implementation("io.github.problem4j:problem4j-jackson2:{version}")
   }
   ```

### Maven dependency for `problem4j-jackson3` (Jackson `3.x`)

[![Sonatype](https://img.shields.io/maven-central/v/io.github.problem4j/problem4j-jackson3?label=problem4j-jackson3)][maven-central-jackson3]

1. Maven:
   ```xml
   <dependencies>
       <dependency>
           <groupId>tools.jackson.core</groupId>
           <artifactId>jackson-databind</artifactId>
           <version>3.1.0</version>
       </dependency>
       <dependency>
           <groupId>io.github.problem4j</groupId>
           <artifactId>problem4j-jackson3</artifactId>
           <version>{version}</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Groovy or Kotlin DSL):
   ```groovy
   dependencies {
       implementation("tools.jackson.core:jackson-databind:3.1.0")
       implementation("io.github.problem4j:problem4j-jackson3:{version}")
   }
   ```

## Project Status

[![Status: Feature Complete](https://img.shields.io/badge/feature%20complete-darkblue?label=status)](#project-status)

**Problem4J Jackson** is considered *feature complete*. Only **bug fixes** will be added. New features may be included
only if there is a strong justification for them; otherwise, future projects are expected to build on this one as a
dependency.

## Problem4J Links

- [`problem4j.github.io`](https://problem4j.github.io) - Full documentation of all projects from Problem4J family.
- [`problem4j-core`][problem4j-core] - Core library defining `Problem` model and `ProblemException`.
- [`problem4j-jackson`][problem4j-jackson] - Jackson module for serializing and deserializing `Problem` objects.
- [`problem4j-spring`][problem4j-spring] - Spring modules extending `ResponseEntityExceptionHandler` for handling
  exceptions and returning `Problem` responses.

## Building from source

<details>
<summary><b>Expand...</b></summary>

Gradle **9.x+** requires **Java 17** or higher to run. For building the project, Gradle automatically picks up **Java
25** via **toolchains** and the `foojay-resolver-convention` plugin. This Java version is needed because the project
uses **ErrorProne** and **NullAway** for static nullness analysis.

The produced artifacts are compatible with **Java 8** thanks to `options.release = 8/17` in Gradle `JavaCompile` tasks.
This means that regardless of the Java version used to run Gradle, the resulting bytecode remains compatible.

- Module `problem4j-jackson2` is compiled with `options.release = 8`, so the produced artifacts are compatible with
  **Java 8**.
- Module `problem4j-jackson3` is compiled with `options.release = 17`, so the produced artifacts are compatible with
  **Java 17**.

The **default Gradle tasks** include `spotlessApply` (for code formatting) and `build` (for compilation and tests).The
simplest way to build the project is to run:

```bash
./gradlew
```

---

To **execute tests** use `test` task. Tests do not change `options.release` so newer Java API can be used.

```bash
./gradlew test
```

---

To **format the code** according to the style defined in [`build.gradle.kts`](./build.gradle.kts) rules use `spotlessApply`
task. **Note** that **building will fail** if code is not properly formatted.

```bash
./gradlew spotlessApply
```

**Note** that if the year has changed, add `-Pspotless.license-year-enabled` flag to update the year in license headers.
The [publishing GitHub Action](.github/workflows/gradle-publish-release.yml) will fail if the year is not updated, but
for local development and builds you can choose to skip it and update the year later.

```bash
./gradlew spotlessApply -Pspotless.license-year-enabled
```

---

To **publish** the built artifacts to **local Maven repository**, use `publishToMavenLocal` task.

```bash
./gradlew publishToMavenLocal
```

Note that for using Maven Local artifacts in target projects, you need to add `mavenLocal()` repository.

```kotlin
repositories {
    mavenLocal()
    mavenCentral()
}
```

---

**Note** that following warnings are to be ignored, as they are caused by the module names containing terminal digits,
which is not recommended but is necessary in this case to differentiate between Jackson 2 and Jackson 3 modules. 
Inclusion of digit is a part of the **module name**, not a versioning scheme.

```txt
> Task :problem4j-jackson2:compileMain9Java
./problem4j-jackson2/src/main9/java/module-info.java:25: warning: [module] module name component jackson2 should avoid terminal digits
module io.github.problem4j.jackson2 {
^
1 warning
```

```txt
> Task :problem4j-jackson3:compileJava
./problem4j-jackson3/src/main/java/module-info.java:25: warning: [module] module name component jackson3 should avoid terminal digits
module io.github.problem4j.jackson3 {
^
1 warning
```

</details>

[jackson]: https://github.com/FasterXML/jackson

[maven-central-jackson2]: https://central.sonatype.com/artifact/io.github.problem4j/problem4j-jackson2

[maven-central-jackson3]: https://central.sonatype.com/artifact/io.github.problem4j/problem4j-jackson3

[problem4j-core]: https://github.com/problem4j/problem4j-core

[problem4j-jackson]: https://github.com/problem4j/problem4j-jackson

[problem4j-spring]: https://github.com/problem4j/problem4j-spring

[com.fasterxml.jackson.databind.Module]: problem4j-jackson2/src/main/resources/META-INF/services/com.fasterxml.jackson.databind.Module

[tools.jackson.databind.JacksonModule]: problem4j-jackson3/src/main/resources/META-INF/services/tools.jackson.databind.JacksonModule

[rfc7807]: https://datatracker.ietf.org/doc/html/rfc7807

[rfc9457]: https://datatracker.ietf.org/doc/html/rfc9457
