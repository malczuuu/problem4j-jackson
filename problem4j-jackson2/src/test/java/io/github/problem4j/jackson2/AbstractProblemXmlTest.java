/*
 * Copyright 2025-2026 The Problem4J Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.problem4j.jackson2;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.problem4j.core.Problem;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

abstract class AbstractProblemXmlTest {

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
