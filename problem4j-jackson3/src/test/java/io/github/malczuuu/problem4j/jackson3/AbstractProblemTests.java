package io.github.malczuuu.problem4j.jackson3;

import io.github.malczuuu.problem4j.core.Problem;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Map;

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
          .extension("object", buildObject())
          .build();

  protected final String json =
      "{\"type\":\"http://localhost/FATAL\",\"title\":\"problem\",\"status\":400,\"detail\":\"A serious problem\",\"instance\":\"http://localhost/endpoint/12\",\"object\":{\"id\":200,\"name\":\"test name\"},\"timestamp\":\"2018-10-01T10:43:21.221Z\",\"userid\":100}";

  private static Object buildObject() {
    Map<String, Object> object = new LinkedHashMap<>();
    object.put("id", 200);
    object.put("name", "test name");
    return object;
  }
}
