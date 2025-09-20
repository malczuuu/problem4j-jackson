package io.github.malczuuu.problem4j.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.problem4j.core.Problem;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class ProblemDeserializerTests extends AbstractProblemTests {

  @Test
  void givenAutoRegisteredModules_whenDeserializing_shouldDeserialize() throws IOException {
    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

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
  void givenManualRegisteredModules_whenDeserializing_shouldDeserialize() throws IOException {
    ObjectMapper mapper = new ObjectMapper().registerModule(new ProblemModule());

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
}
