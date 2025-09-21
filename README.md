# Problem4J Jackson

[![JitPack](https://jitpack.io/v/malczuuu/problem4j-jackson.svg)](https://jitpack.io/#malczuuu/problem4j-jackson)
[![Build Status](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-build.yml)

> Part of [`problem4j`][problem4j] package of libraries.

Jackson integration module for [`problem4j-core`][problem4j-core]. Provides easy support for serializing and
deserializing the `Problem` model using [Jackson's `ObjectMapper`][jackson].

## Table of Contents

- [Features](#features)
- [Example](#example)
- [Usage](#usage)
- [Other Libraries](#other-libraries)

## Features

- ✅ Seamless JSON serialization of `Problem` objects
- ✅ Accurate deserialization into immutable `Problem` instances
- ✅ Compatible with standard Jackson `ObjectMapper`
- ✅ Pluggable via Jackson’s `Module` system
- ✅ Lightweight, with no external dependencies beyond Jackson and `problem4j-core`

## Example

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.problem4j.Problem;
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

This library is available through [Jitpack][jitpack] repository. Add it along with repository in your dependency
manager.

1. Maven:
   ```xml
   <repositories>
       <repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
       </repository>
   </repositories>
   <dependencies>
       <dependency>
           <groupId>com.github.malczuuu</groupId>
           <artifactId>problem4j-jackson</artifactId>
           <version>${problem4j-jackson.version}</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Groovy or Kotlin DSL):
   ```groovy
   repositories {
       maven { url = uri("https://jitpack.io") }
   }
   dependencies {
       implementation("com.github.malczuuu:problem4j-jackson:${problem4j-jackson.version}")
   }
   ```

## Other Libraries

- [`problem4j-core`][problem4j-core] - Core library defining `Problem` model and `ProblemException`.
- [`problem4j-spring-web`][problem4j-spring-web] - Spring Web module extending `ResponseEntityExceptionHandler` for
  handling exceptions and returning `Problem` responses.

[jackson]: https://github.com/FasterXML/jackson

[jitpack]: https://jitpack.io/#malczuuu/problem4j-jackson

[problem4j]: https://github.com/malczuuu/problem4j

[problem4j-core]: https://github.com/malczuuu/problem4j-core

[problem4j-spring-web]: https://github.com/malczuuu/problem4j-spring-web
