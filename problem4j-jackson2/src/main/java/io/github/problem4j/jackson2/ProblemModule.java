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

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.problem4j.core.Problem;

/**
 * Jackson 2 module for Problem. Register this module in your {@code ObjectMapper} to enable
 * serialization and deserialization of {@link Problem} instances.
 *
 * <p>Named differently than in Jackson 3 to avoid accidental misuses when both versions are used in
 * the same project. Name comes from top level class being {@code Module} instead of {@code
 * JacksonModule} as in Jackson 3.
 *
 * @see com.fasterxml.jackson.databind.Module
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see com.fasterxml.jackson.databind.json.JsonMapper
 * @since 1.3.0
 */
public class ProblemModule extends SimpleModule {

  private static final long serialVersionUID = 1L;

  /**
   * Creates and initializes the {@code ProblemModule}.
   *
   * <p>The module is registered under its simple class name and configures Jackson mix-in
   * annotations required for correct {@link Problem} serialization and deserialization.
   *
   * @since 1.3.0
   */
  public ProblemModule() {
    super(ProblemModule.class.getSimpleName());
    setupProblemMixIn();
  }

  /**
   * Configures Jackson mix-in annotations for the {@link Problem} type.
   *
   * <p>Subclasses may override this method to customize or extend the mix-in configuration.
   *
   * @since 1.3.0
   */
  protected void setupProblemMixIn() {
    setMixInAnnotation(Problem.class, ProblemMixIn.class);
  }
}
