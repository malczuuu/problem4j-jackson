package io.github.malczuuu.problem4j.jackson;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.malczuuu.problem4j.core.Problem;

class ConverterBridgeToProblem extends StdConverter<ProblemBridge, Problem> {

  @Override
  public Problem convert(ProblemBridge pojo) {
    return pojo.toProblemBuilder().build();
  }
}
