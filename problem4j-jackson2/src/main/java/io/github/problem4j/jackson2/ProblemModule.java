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
package io.github.problem4j.jackson2;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.problem4j.core.Problem;

/**
 * Jackson 2 module for Problem. Register this module in your {@code ObjectMapper} to enable
 * serialization and deserialization of {@link Problem} instances.
 *
 * <p>Named differently than in Jackson 3 to avoid classpath conflicts when both versions are used
 * in the same project. Name comes from top level class being {@code Module} instead of {@code
 * JacksonModule} as in Jackson 3.
 *
 * @see com.fasterxml.jackson.databind.Module
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see com.fasterxml.jackson.databind.json.JsonMapper
 */
public class ProblemModule extends SimpleModule {

  private static final long serialVersionUID = 1L;

  /**
   * Creates and initializes the {@code ProblemModule}.
   *
   * <p>The module is registered under its simple class name and configures Jackson mix-in
   * annotations required for correct {@link Problem} serialization and deserialization.
   */
  public ProblemModule() {
    super(ProblemModule.class.getSimpleName());
    setupProblemMixIn();
  }

  /**
   * Configures Jackson mix-in annotations for the {@link Problem} type.
   *
   * <p>Subclasses may override this method to customize or extend the mix-in configuration.
   */
  protected void setupProblemMixIn() {
    setMixInAnnotation(Problem.class, ProblemMixIn.class);
  }
}
