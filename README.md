# Problem4J Jackson

[![Build Status](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml)
[![Sonatype](https://img.shields.io/maven-central/v/io.github.malczuuu.problem4j/problem4j-jackson)](https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson)
[![License](https://img.shields.io/github/license/malczuuu/problem4j-jackson)](https://github.com/malczuuu/problem4j-jackson/blob/main/LICENSE)

> Part of [`problem4j`][problem4j] package of libraries.

Jackson integration module for [`problem4j-core`][problem4j-core]. Provides easy support for serializing and
deserializing the `Problem` model using [Jackson's `ObjectMapper`][jackson].

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
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.jackson.ProblemModule;

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
higher is required to use this library.

1. Maven:
   ```xml
   <dependencies>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-jackson</artifactId>
           <version>${problem4j-jackson.version}</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Groovy or Kotlin DSL):
   ```groovy
   dependencies {
       implementation("io.github.malczuuu.problem4j:problem4j-jackson:${problem4j-jackson.version}")
   }
   ```

## Problem4J Links

- [`problem4j`][problem4j] - Documentation repository.
- [`problem4j-core`][problem4j-core] - Core library defining `Problem` model and `ProblemException`.
- [`problem4j-jackson`][problem4j-jackson] - Jackson module for serializing and deserializing `Problem` objects.
- [`problem4j-spring-web`][problem4j-spring-web] - Spring Web module extending `ResponseEntityExceptionHandler` for
  handling exceptions and returning `Problem` responses.

[jackson]: https://github.com/FasterXML/jackson

[maven-central]: https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson

[problem4j]: https://github.com/malczuuu/problem4j

[problem4j-core]: https://github.com/malczuuu/problem4j-core

[problem4j-jackson]: https://github.com/malczuuu/problem4j-jackson

[problem4j-spring-web]: https://github.com/malczuuu/problem4j-spring-web
