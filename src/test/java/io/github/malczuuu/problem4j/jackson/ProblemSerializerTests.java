package io.github.malczuuu.problem4j.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.problem4j.core.Problem;
import org.junit.jupiter.api.Test;

class ProblemSerializerTests extends AbstractProblemTests {

  @Test
  void shouldSerializeProblem() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals(json, problemJson);
  }

  @Test
  void givenManualRegisteredModules_whenSerializing_shouldSerialize()
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper().registerModule(new ProblemModule());

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals(json, problemJson);
  }

  @Test
  void givenOverlappingExtension_whenSerializing_shouldSkipExtension()
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper().registerModule(new ProblemModule());

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
    ObjectMapper mapper = new ObjectMapper().registerModule(new ProblemModule());

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
