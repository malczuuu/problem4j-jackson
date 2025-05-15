package io.github.malczuuu.problem4j.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.malczuuu.problem4j.core.Problem;
import java.io.Serial;

public class ProblemModule extends SimpleModule {

  @Serial private static final long serialVersionUID = 1L;

  public ProblemModule() {
    super(ProblemModule.class.getSimpleName());

    addSerializer(Problem.class, new ProblemSerializer());
    addDeserializer(Problem.class, new ProblemDeserializer());
  }
}
