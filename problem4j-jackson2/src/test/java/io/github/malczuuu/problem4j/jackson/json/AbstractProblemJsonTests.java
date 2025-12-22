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
package io.github.malczuuu.problem4j.jackson.json;

import com.fasterxml.jackson.databind.json.JsonMapper;
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

public abstract class AbstractProblemJsonTests {

  protected final String json =
      "{"
          + "\"type\":\"http://localhost/FATAL\","
          + "\"title\":\"problem\","
          + "\"status\":400,"
          + "\"detail\":\"A serious problem\","
          + "\"instance\":\"http://localhost/endpoint/12\","
          + "\"elements\":[\"A\",\"B\",\"C\"],"
          + "\"object\":{\"id\":200,\"name\":\"test name\"},"
          + "\"timestamp\":\"2018-10-01T10:43:21.221Z\","
          + "\"userid\":100"
          + "}";

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

  protected Object buildObject() {
    Map<String, Object> object = new LinkedHashMap<>();
    object.put("id", 200);
    object.put("name", "test name");
    return object;
  }

  protected static Stream<Arguments> variousJsonMapperConfigurations() {
    return Stream.of(
        Arguments.of(new JsonMapper().findAndRegisterModules()),
        Arguments.of(new JsonMapper().registerModule(new ProblemModule())),
        Arguments.of(new JsonMapper().addMixIn(Problem.class, ProblemMixIn.class)));
  }
}
