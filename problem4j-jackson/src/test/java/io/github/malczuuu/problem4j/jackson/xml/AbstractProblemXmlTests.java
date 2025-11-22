package io.github.malczuuu.problem4j.jackson.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.jackson.ProblemMixIn;
import io.github.malczuuu.problem4j.jackson.ProblemModule;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public abstract class AbstractProblemXmlTests {

  protected final String xml =
      "<problem xmlns=\"urn:ietf:rfc:7807\">"
          + "<type>http://localhost/FATAL</type>"
          + "<title>problem</title>"
          + "<status>400</status>"
          + "<detail>A serious problem</detail>"
          + "<instance>http://localhost/endpoint/12</instance>"
          + "<elements>A</elements>"
          + "<elements>B</elements>"
          + "<elements>C</elements>"
          + "<object>"
          + "<id>200</id>"
          + "<name>test name</name>"
          + "</object>"
          + "<timestamp>2018-10-01T10:43:21.221Z</timestamp>"
          + "<userid>100</userid>"
          + "</problem>";

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
          .extension("elements", List.of("A", "B", "C"))
          .build();

  protected final Problem expectedProblem =
      Problem.builder()
          .type(URI.create("http://localhost/FATAL"))
          .title("problem")
          .status(400)
          .detail("A serious problem")
          .instance(URI.create("http://localhost/endpoint/12"))
          .extension("userid", "100")
          .extension("timestamp", timestamp.toString())
          .extension("object", buildExpectedObject())
          .extension("elements", List.of("A", "B", "C"))
          .build();

  protected Object buildObject() {
    Map<String, Object> object = new LinkedHashMap<>();
    object.put("id", 200);
    object.put("name", "test name");
    return object;
  }

  protected Object buildExpectedObject() {
    Map<String, Object> object = new LinkedHashMap<>();
    object.put("id", "200");
    object.put("name", "test name");
    return object;
  }

  protected static Stream<Arguments> variousXmlMapperConfigurations() {
    return Stream.of(
        Arguments.of(new XmlMapper().findAndRegisterModules()),
        Arguments.of(new XmlMapper().registerModule(new ProblemModule())),
        Arguments.of(new XmlMapper().addMixIn(Problem.class, ProblemMixIn.class)));
  }
}
