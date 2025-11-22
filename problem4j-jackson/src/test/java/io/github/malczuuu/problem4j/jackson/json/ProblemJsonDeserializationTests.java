package io.github.malczuuu.problem4j.jackson.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.jackson.ProblemModule;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProblemJsonDeserializationTests extends AbstractProblemJsonTests {

  @ParameterizedTest
  @MethodSource("variousJsonMapperConfigurations")
  void givenVariousObjectMapper_whenDeserializing_shouldDeserialize(ObjectMapper mapper)
      throws IOException {
    Problem deserializedProblem = mapper.readValue(json, Problem.class);

    assertEquals(problem.getType(), deserializedProblem.getType());
    assertEquals(problem.getTitle(), deserializedProblem.getTitle());
    assertEquals(problem.getStatus(), deserializedProblem.getStatus());
    assertEquals(problem.getDetail(), deserializedProblem.getDetail());
    assertEquals(problem.getInstance(), deserializedProblem.getInstance());

    assertEquals(problem.getExtensions().size(), deserializedProblem.getExtensions().size());

    for (String key : problem.getExtensions()) {
      assertTrue(deserializedProblem.hasExtension(key));
      assertEquals(problem.getExtensionValue(key), deserializedProblem.getExtensionValue(key));
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"http://exa mple.com", "http://example.com/<>", "http://[::1"})
  @NullSource
  void givenTypeInvalidUri_whenDeserializing_shouldDeserialize(String type)
      throws JsonProcessingException {
    ObjectMapper mapper = new JsonMapper().registerModule(new ProblemModule());

    String json =
        "{ "
            + ("\"type\"     : " + quoteIfNotNull(type) + ", ")
            + ("\"title\"    : \"Hello World\", ")
            + ("\"status\"   : 99, ")
            + ("\"instance\" : " + quoteIfNotNull(type))
            + " }";

    Problem problem = mapper.readValue(json, Problem.class);
    assertEquals(Problem.BLANK_TYPE, problem.getType());
    assertEquals("Hello World", problem.getTitle());
    assertEquals(99, problem.getStatus());
    assertNull(problem.getInstance());
  }

  @ParameterizedTest
  @ValueSource(strings = {"\"string\"", "false", "true"})
  @NullSource
  void givenInvalidStatus_whenDeserializing_shouldDeserializeToZero(String status)
      throws JsonProcessingException {
    ObjectMapper mapper = new JsonMapper().registerModule(new ProblemModule());

    String json =
        "{ "
            + ("\"type\"     : " + quoteIfNotNull("http://example.com/type") + ", ")
            + ("\"title\"    : \"Hello World\", ")
            + ("\"status\"   : " + status + ", ")
            + ("\"instance\" : " + quoteIfNotNull("http://example.com/instance"))
            + " }";

    Problem problem = mapper.readValue(json, Problem.class);
    assertEquals(0, problem.getStatus());
  }

  private String quoteIfNotNull(String type) {
    return type != null ? "\"" + type + "\"" : "null";
  }
}
