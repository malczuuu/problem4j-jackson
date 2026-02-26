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

package io.github.problem4j.jackson3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.problem4j.core.Problem;
import java.util.List;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

class ProblemBridgeTest {

  @Test
  void givenTypeAsNumber_whenDeserializing_thenTypeShouldBeBlank() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    String json = "{\"type\":123,\"title\":\"test\",\"status\":400}";

    Problem problem = mapper.readValue(json, Problem.class);

    assertEquals(Problem.BLANK_TYPE, problem.getType());
    assertEquals("test", problem.getTitle());
    assertEquals(400, problem.getStatus());
  }

  @Test
  void givenInstanceAsNumber_whenDeserializing_thenInstanceShouldBeNull() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    String json = "{\"title\":\"test\",\"status\":400,\"instance\":123}";

    Problem problem = mapper.readValue(json, Problem.class);

    assertNull(problem.getInstance());
  }

  @Test
  void givenStatusAsNull_whenDeserializing_thenStatusShouldBeZero() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    String json = "{\"title\":\"test\",\"status\":null}";

    Problem problem = mapper.readValue(json, Problem.class);

    assertEquals(0, problem.getStatus());
  }

  @Test
  void givenExtensionWithNullName_whenSetting_thenExtensionShouldBeIgnored() {
    ProblemBridge bridge = new ProblemBridge();

    bridge.setExtension(null, "value");

    assertTrue(bridge.getExtensions().isEmpty());
  }

  @Test
  void givenExtensionWithNullValue_whenSetting_thenExtensionShouldBeIgnored() {
    ProblemBridge bridge = new ProblemBridge();

    bridge.setExtension("key", null);

    assertTrue(bridge.getExtensions().isEmpty());
  }

  @Test
  @SuppressWarnings("unchecked")
  void givenDuplicateExtension_whenDeserializing_thenValuesShouldBeMergedIntoList() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    String json =
        "{\"title\":\"test\",\"status\":400,"
            + "\"custom\":\"first\",\"custom\":\"second\",\"custom\":\"third\"}";

    Problem problem = mapper.readValue(json, Problem.class);

    Object custom = problem.getExtensionValue("custom");
    assertInstanceOf(List.class, custom);
    assertEquals(List.of("first", "second", "third"), (List<String>) custom);
  }

  @Test
  void givenTypeAsBoolean_whenDeserializing_thenTypeShouldBeBlank() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    String json = "{\"type\":true,\"title\":\"test\",\"status\":400}";

    Problem problem = mapper.readValue(json, Problem.class);

    assertEquals(Problem.BLANK_TYPE, problem.getType());
  }

  @Test
  void givenInstanceAsBoolean_whenDeserializing_thenInstanceShouldBeNull() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    String json = "{\"title\":\"test\",\"status\":400,\"instance\":true}";

    Problem problem = mapper.readValue(json, Problem.class);

    assertNull(problem.getInstance());
  }

  @Test
  void givenTypeAsObject_whenDeserializing_thenTypeShouldBeBlank() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    String json = "{\"type\":{\"nested\":\"value\"},\"title\":\"test\",\"status\":400}";

    Problem problem = mapper.readValue(json, Problem.class);

    assertEquals(Problem.BLANK_TYPE, problem.getType());
  }

  @Test
  void givenInstanceAsArray_whenDeserializing_thenInstanceShouldBeNull() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    String json = "{\"title\":\"test\",\"status\":400,\"instance\":[1,2,3]}";

    Problem problem = mapper.readValue(json, Problem.class);

    assertNull(problem.getInstance());
  }

  @Test
  void givenReservedNameAsExtension_whenSetting_thenExtensionShouldBeIgnored() {
    ProblemBridge bridge = new ProblemBridge();

    bridge.setExtension("type", "someValue");
    bridge.setExtension("title", "someValue");
    bridge.setExtension("status", "someValue");
    bridge.setExtension("detail", "someValue");
    bridge.setExtension("instance", "someValue");

    assertTrue(bridge.getExtensions().isEmpty());
  }
}
