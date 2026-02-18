/*
 * Copyright (c) 2025-2026 The Problem4J Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
