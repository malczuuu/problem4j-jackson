package io.github.malczuuu.problem4j.jackson3;

import io.github.malczuuu.problem4j.core.Problem;
import tools.jackson.databind.util.StdConverter;

class ConverterBridgeToProblem extends StdConverter<ProblemBridge, Problem> {

  @Override
  public Problem convert(ProblemBridge pojo) {
    return pojo.toProblemBuilder().build();
  }
}
