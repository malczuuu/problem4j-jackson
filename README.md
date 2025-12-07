# Problem4J Jackson

[![Build Status](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml)
[![Sonatype](https://img.shields.io/maven-central/v/io.github.malczuuu.problem4j/problem4j-jackson?label=problem4j-jackson)](https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson)
[![Sonatype](https://img.shields.io/maven-central/v/io.github.malczuuu.problem4j/problem4j-jackson3?label=problem4j-jackson3)](https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson3)
[![License](https://img.shields.io/github/license/malczuuu/problem4j-jackson)](https://github.com/malczuuu/problem4j-jackson/blob/main/LICENSE)

Jackson `2.x` and `3.x` integration module for [`problem4j-core`][problem4j-core]. Provides easy support for serializing
and deserializing the `Problem` model using [Jackson's `ObjectMapper`][jackson].

Project contains two submodules, `problem4j-jackson` for Jackson `2.x` and `problem4j-jackson3` for Jackson `3.x`. Both
modules have the similar API and functionality, but are compiled against different versions of Jackson (and by extension
against different versions of Java). Choose the one that matches the version of Jackson you are using in your project.

| Module               | Jackson Version                                     | Java Baseline |
|----------------------|-----------------------------------------------------|---------------|
| `problem4j-jackson`  | `com.fasterxml.jackson.core:jackson-databind:2.x.y` | Java 8        |
| `problem4j-jackson3` | `tools.jackson.core:jackson-databind:3.x.y`         | Java 17       |

Instead of releasing version `2.0`, library was split into two modules, because `jackson-3.x` has different maven
`groupId` so it's technically possible to have both versions included in the same project. **Note** that each module is
versioned independently.

## Table of Contents

- [Features](#features)
- [Example](#example)
- [Usage](#usage)
- [Problem4J Links](#problem4j-links)
- [Building from source](#building-from-source)

## Features

- ✅ Seamless JSON serialization of `Problem` objects.
- ✅ Accurate deserialization into immutable `Problem` instances.
- ✅ Compatible with standard Jackson `ObjectMapper`.
- ✅ Pluggable via Jackson’s `Module` system.
- ✅ Lightweight, with no external dependencies beyond Jackson and `problem4j-core`.

## Example

For `problem4j-jackson` (Jackson `2.x`):

```java
import com.fasterxml.jackson.databind.ObjectMapper;

ObjectMapper mapper = new ObjectMapper().registerModule(new ProblemModule());

Problem problem = Problem.builder().title("Bad Request").status(400).detail("not a valid json").build();

String json = mapper.writeValueAsString(problem);
Problem parsed = mapper.readValue(json, Problem.class);
```

Module is included in [`com.fasterxml.jackson.databind.Module`][com.fasterxml.jackson.databind.Module] for automatic
service discovery. Registration can also be done with `findAndRegisterModules()` method or by adding a `ProblemMixIn`.

```java
ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
ObjectMapper mapper = new ObjectMapper().addMixIn(Problem.class, ProblemMixIn.class);
```

---

For `problem4j-jackson3` (Jackson `3.x`):

```java
import tools.jackson.databind.json.JsonMapper;

JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

Problem problem = Problem.builder().title("Bad Request").status(400).detail("not a valid json").build();

String json = mapper.writeValueAsString(problem);
Problem parsed = mapper.readValue(json, Problem.class);
```

Module is included in [`tools.jackson.databind.JacksonModule`][tools.jackson.databind.JacksonModule] for automatic
service discovery. Registration can also be done with `findAndAddModules()` method or by adding a `ProblemJacksonMixIn`.

```java
JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();
JsonMapper mapper = JsonMapper.builder().addMixIn(Problem.class, ProblemJacksonMixIn.class).build();
```

## Usage

Add library as dependency to Maven or Gradle. See the actual versions on [Maven Central][maven-central]. **Java 8** or
higher is required to use `problem4j-jackson` library. **Java 17** or higher is required to use `problem4j-jackson3`
library.

The `problem4j-jackson` modules does **not** declare `jackson-databind` as a transitive dependency. You should add
`jackson-databind` explicitly as your main Jackson dependency.

For `problem4j-jackson` (**Jackson `2.x`**):

1. Maven:
   ```xml
   <dependencies>
       <dependency>
           <groupId>com.fasterxml.jackson.core</groupId>
           <artifactId>jackson-databind</artifactId>
           <version>2.20.1</version>
       </dependency>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-core</artifactId>
           <version>1.2.3</version>
       </dependency>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-jackson</artifactId>
           <version>1.2.4</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Kotlin DSL):
   ```groovy
   dependencies {
       implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
       implementation("io.github.malczuuu.problem4j:problem4j-core:1.2.3")
       implementation("io.github.malczuuu.problem4j:problem4j-jackson:1.2.4")
   }
   ```

---

For `problem4j-jackson3` (**Jackson `3.x`**):

1. Maven:
   ```xml
   <dependencies>
       <dependency>
           <groupId>tools.jackson.core</groupId>
           <artifactId>jackson-databind</artifactId>
           <version>3.0.3</version>
       </dependency>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-core</artifactId>
           <version>1.2.3</version>
       </dependency>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-jackson3</artifactId>
           <version>1.1.4</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Kotlin DSL):
   ```groovy
   dependencies {
       implementation("tools.jackson.core:jackson-databind:3.0.3")
       implementation("io.github.malczuuu.problem4j:problem4j-core:1.2.3")
       implementation("io.github.malczuuu.problem4j:problem4j-jackson3:1.1.4")
   }
   ```

## Problem4J Links

- [`problem4j-core`][problem4j-core] - Core library defining `Problem` model and `ProblemException`.
- [`problem4j-jackson`][problem4j-jackson] - Jackson module for serializing and deserializing `Problem` objects.
- [`problem4j-spring`][problem4j-spring] - Spring modules extending `ResponseEntityExceptionHandler` for handling
  exceptions and returning `Problem` responses.

## Building from source

<details>
<summary><b>Expand...</b></summary>

Gradle **9.x+** requires **Java 17+** to run, but higher Java versions can also be used.

- Module `problem4j-jackson` is compiled using a **Java 8 toolchain**, so the produced artifacts are compatible with
  **Java 8**.
- Module `problem4j-jackson3` is compiled using a **Java 17 toolchain**, so the produced artifacts are compatible with
  **Java 17**.

```bash
./gradlew clean build
```

To execute tests use `test` task.

```bash
./gradlew clean test
```

To format the code according to the style defined in [`build.gradle.kts`](./build.gradle.kts) rules use `spotlessApply`
task. **Note** that **building will fail** if code is not properly formatted.

```bash
./gradlew spotlessApply
```

To publish the built artifacts to local Maven repository, run following command, replacing `XXXX` with the desired
version. By default, the version is derived from git commit hash.

```bash
./gradlew -Pversion=XXXX clean build publishToMavenLocal
```

</details>

[jackson]: https://github.com/FasterXML/jackson

[maven-central]: https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson

[problem4j-core]: https://github.com/malczuuu/problem4j-core

[problem4j-jackson]: https://github.com/malczuuu/problem4j-jackson

[problem4j-spring]: https://github.com/malczuuu/problem4j-spring

[com.fasterxml.jackson.databind.Module]: problem4j-jackson/src/main/resources/META-INF/services/com.fasterxml.jackson.databind.Module

[tools.jackson.databind.JacksonModule]: problem4j-jackson3/src/main/resources/META-INF/services/tools.jackson.databind.JacksonModule
