/*
 * Copyright 2025-present the original author or authors.
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

package io.github.problem4j.jackson3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.problem4j.core.Problem;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.xml.XmlMapper;

class ProblemXmlDeserializationTest extends AbstractProblemXmlTest {

  @ParameterizedTest
  @MethodSource("variousXmlMapperConfigurations")
  void givenVariousObjectMapper_whenDeserializing_shouldDeserialize(XmlMapper mapper) {
    Problem deserializedProblem = mapper.readValue(xml, Problem.class);

    assertEquals(expectedProblem.getType(), deserializedProblem.getType());
    assertEquals(expectedProblem.getTitle(), deserializedProblem.getTitle());
    assertEquals(expectedProblem.getStatus(), deserializedProblem.getStatus());
    assertEquals(expectedProblem.getDetail(), deserializedProblem.getDetail());
    assertEquals(expectedProblem.getInstance(), deserializedProblem.getInstance());

    assertEquals(
        expectedProblem.getExtensions().size(), deserializedProblem.getExtensions().size());

    for (String key : expectedProblem.getExtensions().keySet()) {
      assertTrue(deserializedProblem.getExtensions().containsKey(key));
      assertEquals(
          expectedProblem.getExtensions().get(key), deserializedProblem.getExtensions().get(key));
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"http://exa mple.com", "http://example.com/&lt;&gt;", "http://[::1"})
  @NullSource
  void givenTypeInvalidUri_whenDeserializing_shouldDeserialize(String type) {
    ObjectMapper mapper = XmlMapper.builder().addModule(new ProblemJacksonModule()).build();

    String xml =
        "<problem>"
            + (type != null ? "<type>" + type + "</type>" : "")
            + "<title>Hello World</title>"
            + "<status>99</status>"
            + (type != null ? "<instance>" + type + "</instance>" : "")
            + "</problem>";

    Problem problem = mapper.readValue(xml, Problem.class);
    assertEquals(Problem.BLANK_TYPE, problem.getType());
    assertEquals("Hello World", problem.getTitle());
    assertEquals(99, problem.getStatus());
    assertNull(problem.getInstance());
  }

  @ParameterizedTest
  @ValueSource(strings = {"\"string\"", "false", "true"})
  @NullSource
  void givenInvalidStatus_whenDeserializing_shouldDeserializeToZero(String status) {
    ObjectMapper mapper = XmlMapper.builder().addModule(new ProblemJacksonModule()).build();

    String xml =
        "<problem>"
            + "<type>http://example.com/type</type>"
            + "<title>Hello World</title>"
            + (status != null ? ("<status>" + status + "</status>") : "")
            + "<instance>http://example.com/instance</instance>"
            + "</problem>";

    Problem problem = mapper.readValue(xml, Problem.class);
    assertEquals(0, problem.getStatus());
  }
}
