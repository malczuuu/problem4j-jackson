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

import io.github.problem4j.core.Problem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.jackson.databind.json.JsonMapper;

class ProblemJsonSerializerTest extends AbstractProblemJsonTest {

  @ParameterizedTest
  @MethodSource("variousJsonMapperConfigurations")
  void givenVariousObjectMapper_whenSerializing_shouldSerialize(JsonMapper mapper) {
    String problemJson = mapper.writeValueAsString(problem);

    assertEquals(json, problemJson);
  }

  @Test
  void givenOverlappingExtension_whenSerializing_shouldSkipExtension() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    Problem problem =
        Problem.builder()
            .title("Hello World")
            .status(99)
            .extension(Problem.extension("title", "extTitle"))
            .build();

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals("{\"title\":\"Hello World\",\"status\":99}", problemJson);
  }

  @Test
  void givenExtensionWithNullValue_whenSerializing_shouldSkipExtension() {
    JsonMapper mapper = JsonMapper.builder().addModule(new ProblemJacksonModule()).build();

    Problem problem =
        Problem.builder()
            .title("Hello World")
            .status(99)
            .extension(Problem.extension("extension", null))
            .build();

    String problemJson = mapper.writeValueAsString(problem);

    assertEquals("{\"title\":\"Hello World\",\"status\":99}", problemJson);
  }
}
