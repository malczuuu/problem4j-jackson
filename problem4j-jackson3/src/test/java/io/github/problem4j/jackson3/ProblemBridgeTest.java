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

    Object custom = problem.getExtensions().get("custom");
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
