# Problem4J Jackson

[![Build Status](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml)
[![Sonatype](https://img.shields.io/maven-central/v/io.github.malczuuu.problem4j/problem4j-jackson)](https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson)
[![License](https://img.shields.io/github/license/malczuuu/problem4j-jackson)](https://github.com/malczuuu/problem4j-jackson/blob/main/LICENSE)

Jackson `2.x` and `3.x` integration module for [`problem4j-core`][problem4j-core]. Provides easy support for serializing
and deserializing the `Problem` model using [Jackson's `ObjectMapper`][jackson].

Project contains two submodules, one for Jackson `2.x` and another for Jackson `3.x`. Both modules have the same API
and functionality, but are compiled against different versions of Jackson (and by extension against different versions
of Java). Choose the one that matches the version of Jackson you are using in your project. Separation was done because `jackson-3.x` has different maven `groupId` so it's
technically not possible to have both versions included in the same project.

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

```java
public class ExampleClass {

  public void method() {
    ObjectMapper mapper = new ObjectMapper().registerModule(new ProblemModule());

    Problem problem =
        Problem.builder()
            .type("https://example.com/errors/invalid-request")
            .title("Invalid Request")
            .status(400)
            .detail("not a valid json")
            .instance("https://example.com/instances/1234")
            .build();

    String json = mapper.writeValueAsString(problem);
    Problem parsed = mapper.readValue(json, Problem.class);
  }
}
```

## Usage

Add library as dependency to Maven or Gradle. See the actual versions on [Maven Central][maven-central]. **Java 8** or
higher is required to use `problem4j-jackson` library. **Java 17** or higher is required to use `problem4j-jackson3`
library.

The `problem4j-jackson` module does **not** declare `jackson-databind` as a transitive dependency. You should add
`jackson-databind` explicitly as your main Jackson dependency.

This module serves as an extension to `jackson-databind` and has been verified to work with versions `2.10.0` or
newer, though it's recommended to use the latest available release.

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
