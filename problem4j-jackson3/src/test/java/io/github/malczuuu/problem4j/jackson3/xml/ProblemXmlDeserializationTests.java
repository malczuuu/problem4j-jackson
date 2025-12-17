/*
 * Copyright (c) 2025 Damian Malczewski
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
 * SPDX-License-Identifier: MIT
 */
package io.github.malczuuu.problem4j.jackson3.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.jackson3.ProblemJacksonModule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.xml.XmlMapper;

class ProblemXmlDeserializationTests extends AbstractProblemXmlTests {

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

    for (String key : expectedProblem.getExtensions()) {
      assertTrue(deserializedProblem.hasExtension(key));
      assertEquals(
          expectedProblem.getExtensionValue(key), deserializedProblem.getExtensionValue(key));
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
