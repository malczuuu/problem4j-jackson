package io.github.malczuuu.problem4j.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.malczuuu.problem4j.core.Problem;

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

  public ProblemModule() {
    super(ProblemModule.class.getSimpleName());

    addSerializer(Problem.class, new ProblemSerializer());
    addDeserializer(Problem.class, new ProblemDeserializer());
  }
}
