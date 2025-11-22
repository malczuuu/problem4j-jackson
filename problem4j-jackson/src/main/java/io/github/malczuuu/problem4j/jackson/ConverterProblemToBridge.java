package io.github.malczuuu.problem4j.jackson;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.malczuuu.problem4j.core.Problem;

class ConverterProblemToBridge extends StdConverter<Problem, ProblemBridge> {

  @Override
  public ProblemBridge convert(Problem value) {
    return new ProblemBridge(value);
  }
}
