package io.github.malczuuu.problem4j.jackson3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.malczuuu.problem4j.core.Problem;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

class ProblemSerializerTests extends AbstractProblemTests {

  @Test
  void shouldSerializeProblem() {
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals(json, problemJson);
  }

  @Test
  void givenManualRegisteredModules_whenSerializing_shouldSerialize() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals(json, problemJson);
  }

  @Test
  void givenOverlappingExtension_whenSerializing_shouldSkipExtension() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

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
  void givenExtensionWithNullValue_whenSerializing_shouldSkipExtension() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

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
