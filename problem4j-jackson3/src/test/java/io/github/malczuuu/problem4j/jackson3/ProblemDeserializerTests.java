package io.github.malczuuu.problem4j.jackson3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.malczuuu.problem4j.core.Problem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import tools.jackson.databind.json.JsonMapper;

class ProblemDeserializerTests extends AbstractProblemTests {

  @Test
  void givenAutoRegisteredModules_whenDeserializing_shouldDeserialize() {
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

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

  @Test
  void givenManualRegisteredModules_whenDeserializing_shouldDeserialize() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

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
  void givenTypeInvalidUri_whenDeserializing_shouldDeserialize(String type) {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

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

  private String quoteIfNotNull(String type) {
    return type != null ? "\"" + type + "\"" : "null";
  }
}
