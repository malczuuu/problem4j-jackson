package io.github.malczuuu.problem4j.jackson3;

import io.github.malczuuu.problem4j.core.Problem;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public abstract class AbstractProblemTests {

  protected final Instant timestamp =
      LocalDateTime.of(2018, 10, 1, 10, 43, 21, 221000000).toInstant(ZoneOffset.UTC);

  protected final Problem problem =
      Problem.builder()
          .type(URI.create("http://localhost/FATAL"))
          .title("problem")
          .status(400)
          .detail("A serious problem")
          .instance(URI.create("http://localhost/endpoint/12"))
          .extension("userid", 100)
          .extension("timestamp", timestamp.toString())
          .build();

  protected final String json =
      "{\"type\":\"http://localhost/FATAL\",\"title\":\"problem\",\"status\":400,\"detail\":\"A serious problem\",\"instance\":\"http://localhost/endpoint/12\",\"timestamp\":\"2018-10-01T10:43:21.221Z\",\"userid\":100}";
}
