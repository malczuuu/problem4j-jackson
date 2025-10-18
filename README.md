# Problem4J Jackson

[![Build Status](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml)
[![Sonatype](https://img.shields.io/maven-central/v/io.github.malczuuu.problem4j/problem4j-jackson?label=problem4j-jackson)](https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson)
[![Sonatype](https://img.shields.io/maven-central/v/io.github.malczuuu.problem4j/problem4j-jackson3?label=problem4j-jackson3)](https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson3)
[![License](https://img.shields.io/github/license/malczuuu/problem4j-jackson)](https://github.com/malczuuu/problem4j-jackson/blob/main/LICENSE)

Jackson `2.x` and `3.x` integration module for [`problem4j-core`][problem4j-core]. Provides easy support for serializing
and deserializing the `Problem` model using [Jackson's `ObjectMapper`][jackson].

Project contains two submodules, `problem4j-jackson` for Jackson `2.x` and `problem4j-jackson3` for Jackson `3.x`. Both
modules have the same API and functionality, but are compiled against different versions of Jackson (and by extension
against different versions of Java). Choose the one that matches the version of Jackson you are using in your project.

Instead of releasing version `2.0`, library was split into two modules, because `jackson-3.x` has different maven
`groupId` so it's technically possible to have both versions included in the same project. **Note** that each module is
versioned independently.

| Module               | Jackson Version                                     | Java Baseline |
|----------------------|-----------------------------------------------------|---------------|
| `problem4j-jackson`  | `com.fasterxml.jackson.core:jackson-databind:2.x.y` | Java 8        |
| `problem4j-jackson3` | `tools.jackson.core:jackson-databind:3.0.0`         | Java 17       |

## Table of Contents

- [Features](#features)
- [Example](#example)
- [Usage](#usage)
- [Problem4J Links](#problem4j-links)

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

For `problem4j-jackson3` (Jackson `3.x`):

```java
import tools.jackson.databind.json.JsonMapper;

JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

Problem problem = Problem.builder().title("Bad Request").status(400).detail("not a valid json").build();

String json = mapper.writeValueAsString(problem);
Problem parsed = mapper.readValue(json, Problem.class);
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
           <version>2.20.0</version>
       </dependency>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-jackson</artifactId>
           <version>1.1.0</version>
       </dependency>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-core</artifactId>
           <version>1.1.0</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Kotlin DSL):
   ```groovy
   dependencies {
       implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
       implementation("io.github.malczuuu.problem4j:problem4j-jackson:1.1.0")
       implementation("io.github.malczuuu.problem4j:problem4j-core:1.1.0")
   }
   ```

For `problem4j-jackson3` (**Jackson `3.x`**):

1. Maven:
   ```xml
   <dependencies>
       <dependency>
           <groupId>tools.jackson.core</groupId>
           <artifactId>jackson-databind</artifactId>
           <version>2.20.0</version>
       </dependency>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-jackson3</artifactId>
           <version>1.0.0</version>
       </dependency>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-core</artifactId>
           <version>1.1.0</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Kotlin DSL):
   ```groovy
   dependencies {
       implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
       implementation("io.github.malczuuu.problem4j:problem4j-jackson3:1.0.0")
       implementation("io.github.malczuuu.problem4j:problem4j-core:1.1.0")
   }
   ```

For using snapshot versions [**Snapshots** chapter of`PUBLISHING.md`](PUBLISHING.md#snapshots).

## Problem4J Links

- [`problem4j-core`][problem4j-core] - Core library defining `Problem` model and `ProblemException`.
- [`problem4j-jackson`][problem4j-jackson] - Jackson module for serializing and deserializing `Problem` objects.
- [`problem4j-spring`][problem4j-spring] - Spring modules extending `ResponseEntityExceptionHandler` for handling
  exceptions and returning `Problem` responses.

[jackson]: https://github.com/FasterXML/jackson

[maven-central]: https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson

[problem4j-core]: https://github.com/malczuuu/problem4j-core

[problem4j-jackson]: https://github.com/malczuuu/problem4j-jackson

[problem4j-spring]: https://github.com/malczuuu/problem4j-spring
