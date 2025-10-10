package io.github.malczuuu.problem4j.jackson3;

import io.github.malczuuu.problem4j.core.Problem;
import tools.jackson.databind.module.SimpleModule;

public class ProblemJacksonModule extends SimpleModule {

  public ProblemJacksonModule() {
    super(ProblemJacksonModule.class.getSimpleName());

    addSerializer(Problem.class, new ProblemSerializer());
    addDeserializer(Problem.class, new ProblemDeserializer());
  }
}
