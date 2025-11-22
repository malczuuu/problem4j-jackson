package io.github.malczuuu.problem4j.jackson3;

import io.github.malczuuu.problem4j.core.Problem;
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

  public ProblemJacksonModule() {
    super(ProblemJacksonModule.class.getSimpleName());

    setMixInAnnotation(Problem.class, ProblemMixIn.class);
  }
}
