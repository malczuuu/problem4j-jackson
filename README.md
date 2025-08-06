# Problem4J Jackson

[![JitPack](https://jitpack.io/v/malczuuu/problem4j-jackson.svg)](https://jitpack.io/#malczuuu/problem4j-jackson)
[![Build Status](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle.yml/badge.svg)](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle.yml)
[![Weekly Build Status](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-weekly.yml/badge.svg)](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-weekly.yml)

> Part of [`problem4j`][problem4j] package of libraries.

Jackson integration module for [`problem4j-core`][problem4j-core]. Provides easy support for serializing and
deserializing the `Problem` model using [Jackson's `ObjectMapper`][jackson].

## Features

- ✅ Seamless JSON serialization of `Problem` objects
- ✅ Accurate deserialization into immutable `Problem` instances
- ✅ Compatible with standard Jackson `ObjectMapper`
- ✅ Pluggable via Jackson’s `Module` system
- ✅ Lightweight, with no external dependencies beyond Jackson and `problem4j-core`

## Example

```java
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new ProblemModule());

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
```

## Usage

This library is available through [Jitpack][jitpack] repository. Add it along with repository in your dependency
manager.

```groovy
// build.gradle

repositories {
    // ...
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // ...
    implementation("com.github.malczuuu:problem4j-core:<problem4j-core-version>")
    implementation("com.github.malczuuu:problem4j-jackson:<problem4j-jackson-version>")
}
```

[problem4j]: https://github.com/malczuuu/problem4j

[problem4j-core]: https://github.com/malczuuu/problem4j-core

[jackson]: https://github.com/FasterXML/jackson

[jitpack]: https://jitpack.io/#malczuuu/problem4j-jackson
