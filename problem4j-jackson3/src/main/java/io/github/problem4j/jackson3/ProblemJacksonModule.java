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
package io.github.problem4j.jackson3;

import io.github.problem4j.core.Problem;
import tools.jackson.databind.module.SimpleModule;

/**
 * Jackson 3 module for Problem. Register this module in your {@code ObjectMapper} to enable
 * serialization and deserialization of {@link io.github.problem4j.core.Problem} instances.
 *
 * <p>Named differently than in Jackson 2 to avoid classpath conflicts when both versions are used
 * in the same project. Name comes from top level class being {@code JacksonModule} instead of
 * {@code Module} as in Jackson 2.
 *
 * @see tools.jackson.databind.JacksonModule
 * @see tools.jackson.databind.ObjectMapper
 * @see tools.jackson.databind.json.JsonMapper
 */
public class ProblemJacksonModule extends SimpleModule {

  public ProblemJacksonModule() {
    super(ProblemJacksonModule.class.getSimpleName());
    setupProblemJacksonMixIn();
  }

  protected void setupProblemJacksonMixIn() {
    setMixInAnnotation(Problem.class, ProblemJacksonMixIn.class);
  }
}
