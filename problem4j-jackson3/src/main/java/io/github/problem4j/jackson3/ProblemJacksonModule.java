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

package io.github.problem4j.jackson3;

import io.github.problem4j.core.Problem;
import java.io.Serial;
import tools.jackson.databind.module.SimpleModule;

/**
 * Jackson 3 module for Problem. Register this module in your {@code ObjectMapper} to enable
 * serialization and deserialization of {@link Problem} instances.
 *
 * <p>Named differently than in Jackson 2 to avoid accidental misuses when both versions are used in
 * the same project. Name comes from top level class being {@code JacksonModule} instead of {@code
 * Module} as in Jackson 2.
 *
 * @see tools.jackson.databind.JacksonModule
 * @see tools.jackson.databind.ObjectMapper
 * @see tools.jackson.databind.json.JsonMapper
 * @since 1.3.0
 */
public class ProblemJacksonModule extends SimpleModule {

  @Serial private static final long serialVersionUID = 1L;

  /**
   * Creates and initializes the {@code ProblemJacksonModule}.
   *
   * <p>The module is registered under its simple class name and configures Jackson mix-in
   * annotations required for correct {@link Problem} serialization and deserialization.
   *
   * @since 1.3.0
   */
  public ProblemJacksonModule() {
    super(ProblemJacksonModule.class.getSimpleName());
    setupProblemJacksonMixIn();
  }

  /**
   * Configures Jackson mix-in annotations for the {@link Problem} type.
   *
   * <p>Subclasses may override this method to customize or extend the mix-in configuration.
   *
   * @since 1.3.0
   */
  protected void setupProblemJacksonMixIn() {
    setMixInAnnotation(Problem.class, ProblemJacksonMixIn.class);
  }
}
