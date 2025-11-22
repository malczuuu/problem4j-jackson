package io.github.malczuuu.problem4j.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.malczuuu.problem4j.core.Problem;
import org.junit.jupiter.api.Test;

class ProblemJsonSerializationTests extends AbstractProblemJsonTests {

  @Test
  void shouldSerializeProblem() throws JsonProcessingException {
    ObjectMapper mapper = new JsonMapper().findAndRegisterModules();

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals(json, problemJson);
  }

  @Test
  void givenManualRegisteredModules_whenSerializing_shouldSerialize()
      throws JsonProcessingException {
    ObjectMapper mapper = new JsonMapper().registerModule(new ProblemModule());

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals(json, problemJson);
  }

  @Test
  void givenOverlappingExtension_whenSerializing_shouldSkipExtension()
      throws JsonProcessingException {
    ObjectMapper mapper = new JsonMapper().registerModule(new ProblemModule());

    Problem problem =
        Problem.builder()
            .title("Hello World")
            .status(99)
            .extension(Problem.extension("title", "extTitle"))
            .build();

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals("{\"title\":\"Hello World\",\"status\":99}", problemJson);
  }

  @Test
  void givenExtensionWithNullValue_whenSerializing_shouldSkipExtension()
      throws JsonProcessingException {
    ObjectMapper mapper = new JsonMapper().registerModule(new ProblemModule());

    Problem problem =
        Problem.builder()
            .title("Hello World")
            .status(99)
            .extension(Problem.extension("extension", null))
            .build();

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals("{\"title\":\"Hello World\",\"status\":99}", problemJson);
  }
}
