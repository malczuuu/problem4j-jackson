package io.github.malczuuu.problem4j.jackson3;

import io.github.malczuuu.problem4j.core.Problem;
import tools.jackson.databind.util.StdConverter;

class ConverterProblemToBridge extends StdConverter<Problem, ProblemBridge> {

  @Override
  public ProblemBridge convert(Problem value) {
    return new ProblemBridge(value);
  }
}
