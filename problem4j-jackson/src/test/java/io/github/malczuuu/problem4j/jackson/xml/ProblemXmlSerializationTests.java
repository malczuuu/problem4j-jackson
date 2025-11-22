package io.github.malczuuu.problem4j.jackson.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.jackson.ProblemModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ProblemXmlSerializationTests extends AbstractProblemXmlTests {

  @ParameterizedTest
  @MethodSource("variousXmlMapperConfigurations")
  void givenVariousObjectMapper_whenDeserializing_shouldSerializeProblem(ObjectMapper mapper)
      throws JsonProcessingException {
    String problemXml = mapper.writeValueAsString(problem);

    assertEquals(xml, problemXml);
  }

  @Test
  void givenOverlappingExtension_whenSerializing_shouldSkipExtension()
      throws JsonProcessingException {
    ObjectMapper mapper = new XmlMapper().registerModule(new ProblemModule());

    Problem problem =
        Problem.builder()
            .title("Hello World")
            .status(99)
            .extension(Problem.extension("title", "extTitle"))
            .build();

    String problemXml = mapper.writeValueAsString(problem);

    assertEquals(
        "<problem xmlns=\"urn:ietf:rfc:7807\"><title>Hello World</title><status>99</status></problem>",
        problemXml);
  }

  @Test
  void givenExtensionWithNullValue_whenSerializing_shouldSkipExtension()
      throws JsonProcessingException {
    ObjectMapper mapper = new XmlMapper().registerModule(new ProblemModule());

    Problem problem =
        Problem.builder()
            .title("Hello World")
            .status(99)
            .extension(Problem.extension("extension", null))
            .build();

    String problemXml = mapper.writeValueAsString(problem);

    assertEquals(
        "<problem xmlns=\"urn:ietf:rfc:7807\"><title>Hello World</title><status>99</status></problem>",
        problemXml);
  }
}
