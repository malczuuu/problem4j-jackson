package io.github.malczuuu.problem4j.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.malczuuu.problem4j.core.Problem;

public class ProblemModule extends SimpleModule {

  private static final long serialVersionUID = 1L;

  public ProblemModule() {
    super(ProblemModule.class.getSimpleName());

    addSerializer(Problem.class, new ProblemSerializer());
    addDeserializer(Problem.class, new ProblemDeserializer());
  }
}
