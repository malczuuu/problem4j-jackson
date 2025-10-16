# Publishing

## Snapshots

See [`gradle-publish-snapshot.yml`](.github/workflows/gradle-publish-snapshot.yml) for publishing snapshot version
instructions. Workflow requires manual trigger for snapshot build so it's not published regularly.

Artifacts are published to Snapshot Repository, using following Gradle task.

```bash
./gradlew -Pversion=<version> publishAggregationToCentralPortalSnapshots
```

### Accessing SNAPSHOT versions

1. Maven:
   ```xml
   <repositories>
       <repository>
           <id>maven-central</id>
           <url>https://repo.maven.apache.org/maven2/</url>
       </repository>
   
       <!-- add snapshot repository (for unpublished or nightly builds) -->
       <repository>
           <id>sonatype-snapshots</id>
           <url>https://central.sonatype.com/repository/maven-snapshots/</url>
           <releases>
               <enabled>false</enabled>
           </releases>
           <snapshots>
               <enabled>true</enabled>
               <!-- always check for new snapshots -->
               <updatePolicy>always</updatePolicy>
           </snapshots>
       </repository>
   </repositories>
   
   <dependencies>
   <dependency>
       <groupId>io.github.malczuuu.problem4j</groupId>
       <artifactId>problem4j-jackson</artifactId>
       <version>1.2.0-SNAPSHOT</version>
   </dependency>
   </dependencies>
   ```
2. Gradle (Kotlin DSL):
   ```kotlin
   repositories {
       mavenCentral()
   
       maven {
           url = uri("https://central.sonatype.com/repository/maven-snapshots/")
           content {
               includeGroup("io.github.malczuuu.problem4j")
           }
           mavenContent {
               snapshotsOnly()
           }
       }
   }
   
   configurations.all {
       resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
   }
   
   dependencies {
       implementation("io.github.malczuuu.problem4j:problem4j-jackson:1.2.0-SNAPSHOT") {
           isChanging = true   
       }
       implementation("io.github.malczuuu.problem4j:problem4j-jackson3:1.0.0-SNAPSHOT") {
           isChanging = true   
       }
   }
   ```

## Releases

Keep Git tags with `{moduleName}-vX.Y.Z-{suffix}` format. GitHub Actions job will only trigger on such tags and will
evaluate module and version based on tag format.

See [`gradle-publish-release.yml`](.github/workflows/gradle-publish-release.yml) for whole publishing procedure.

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
./gradlew -Pversion=<version> -Psign publishAllPublicationsToCentralPortal
```

This command uses `nmcp` Gradle plugin - [link](https://github.com/GradleUp/nmcp).

**Note** that this only uploads the artifacts to Sonatype repository. You need to manually log in to Sonatype to push
the artifacts to Maven Central.
