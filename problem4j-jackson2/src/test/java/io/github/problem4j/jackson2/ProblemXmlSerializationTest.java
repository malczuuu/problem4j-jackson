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

package io.github.problem4j.jackson2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.problem4j.core.Problem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ProblemXmlSerializationTest extends AbstractProblemXmlTest {

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
