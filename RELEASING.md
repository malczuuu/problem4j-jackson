# Publishing

## Maven Central

[![Publish Release Status](https://github.com/malczuuu/problem4j-core/actions/workflows/gradle-publish-release.yml/badge.svg)][gradle-publish-release]
[![Sonatype](https://img.shields.io/maven-central/v/io.github.malczuuu.problem4j/problem4j-jackson?label=problem4j-jackson)][maven-central-problem4j-jackson]
[![Sonatype](https://img.shields.io/maven-central/v/io.github.malczuuu.problem4j/problem4j-jackson3?label=problem4j-jackson3)][maven-central-problem4j-jackson3]

1. Keep Git tags with `{moduleName}-vX.Y.Z-{suffix}` format. GitHub Actions job will only trigger on such tags and will
   evaluate module and version based on tag format.
2. The releasing procedure only uploads the artifacts to Sonatype repository. You need to manually log in to Sonatype to
   push the artifacts to Maven Central.

See [`gradle-publish-release.yml`][gradle-publish-release.yml] for publishing release versions instructions.

Set the following environment variables in your CI/CD (GitHub Actions, etc.):

```txt
# generated on Sonatype account
PUBLISHING_USERNAME=<username>
PUBLISHING_PASSWORD=<password>

# generated PGP key for signing artifacts
SIGNING_KEY=<PGP key>
SIGNING_PASSWORD=<PGP password>
```

Artifacts are published to Maven Central via Sonatype, using following Gradle task.

```bash
./gradlew -Pversion=<version> -Psign problem4j-jackson:publishAllPublicationsToCentralPortal
./gradlew -Pversion=<version> -Psign problem4j-jackson3:publishAllPublicationsToCentralPortal
```

This command uses `nmcp` Gradle plugin - [link](https://github.com/GradleUp/nmcp).

[gradle-publish-release]: https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-publish-release.yml

[gradle-publish-release.yml]: .github/workflows/gradle-publish-release.yml

[maven-central-problem4j-jackson]: https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson

[maven-central-problem4j-jackson3]: https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-jackson3
