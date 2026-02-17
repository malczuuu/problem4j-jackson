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

import io.github.problem4j.core.Problem;
import java.io.Serial;
import tools.jackson.databind.module.SimpleModule;

/**
 * Jackson 3 module for Problem. Register this module in your {@code ObjectMapper} to enable
 * serialization and deserialization of {@link Problem} instances.
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

  @Serial private static final long serialVersionUID = 1L;

  /**
   * Creates and initializes the {@code ProblemJacksonModule}.
   *
   * <p>The module is registered under its simple class name and configures Jackson mix-in
   * annotations required for correct {@link Problem} serialization and deserialization.
   */
  public ProblemJacksonModule() {
    super(ProblemJacksonModule.class.getSimpleName());
    setupProblemJacksonMixIn();
  }

  /**
   * Configures Jackson mix-in annotations for the {@link Problem} type.
   *
   * <p>Subclasses may override this method to customize or extend the mix-in configuration.
   */
  protected void setupProblemJacksonMixIn() {
    setMixInAnnotation(Problem.class, ProblemJacksonMixIn.class);
  }
}
