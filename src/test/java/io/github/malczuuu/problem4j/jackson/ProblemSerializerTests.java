package io.github.malczuuu.problem4j.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
