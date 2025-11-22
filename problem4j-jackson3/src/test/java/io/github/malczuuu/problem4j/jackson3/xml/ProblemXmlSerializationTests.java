package io.github.malczuuu.problem4j.jackson3.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.jackson3.ProblemJacksonModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.xml.XmlMapper;

class ProblemXmlSerializationTests extends AbstractProblemXmlTests {

  @ParameterizedTest
  @MethodSource("variousXmlMapperConfigurations")
  void givenVariousObjectMapper_whenSerializing_shouldSerialize(XmlMapper mapper) {
    String problemXml = mapper.writeValueAsString(problem);

    assertEquals(xml, problemXml);
  }

  @Test
  void givenOverlappingExtension_whenSerializing_shouldSkipExtension() {
    ObjectMapper mapper = XmlMapper.builder().addModule(new ProblemJacksonModule()).build();

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
  void givenExtensionWithNullValue_whenSerializing_shouldSkipExtension() {
    ObjectMapper mapper = XmlMapper.builder().addModule(new ProblemJacksonModule()).build();

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
