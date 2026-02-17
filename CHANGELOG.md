# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog][keepachangelog], and this project adheres to [Semantic Versioning][semver].

## [Unreleased]

## [1.4.1] - 2026-02-17

### Changed

- Bump `problem4j-core` to `1.4.1`.

## [1.4.0] - 2026-02-17

### Added

- Add support for JSpecify annotations for nullability and Kotlin interop.
- Add support for **Java Platform Module System** if using Java version 9+, due to producing multi-release JAR
  artifacts.
  ```java
  module org.example.project {
      // pick what you need for your project
      requires io.github.problem4j.jackson2;
      requires io.github.problem4j.jackson3;
  }
  ```
  
### Changed

- Bump `problem4j-core` to `1.4.0`.

## [1.3.3] - 2026-02-12

### Changed

- Bump `problem4j-core` to `1.3.3`.

## [1.3.2] - 2026-01-29

### Changed

- Bump `problem4j-core` to `1.3.2`.

## [1.3.1] - 2026-01-13

### Changed

- Bump `problem4j-core` to `1.3.1`.

## [1.3.0] - 2025-12-24

This release of `problem4j-jackson` is considered a first "public" release, so the entry aggregates changes from the
`v1.0.x` to `v1.2.x` release lines into single entry.

### Added

- Add modules for serialization and deserialization of `Problem` instances using Jackson. Supports both Jackson 2.x and
  Jackson 3.x by two separate submodules - `problem4j-jackson-2` and `problem4j-jackson-3`.
- Add `MixIn` interfaces for configuring `Problem` serialization/deserialization without loading module if necessary.
- Register modules in `META-INF/services` for automatic discovery by Jackson, so no additional configuration is needed
  to use them.

[keepachangelog]: https://keepachangelog.com/en/1.1.0/

[semver]: https://semver.org/spec/v2.0.0.html
