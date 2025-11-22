package io.github.malczuuu.problem4j.jackson3;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static io.github.malczuuu.problem4j.jackson3.ProblemBridge.NAMESPACE;
import static io.github.malczuuu.problem4j.jackson3.ProblemBridge.PROBLEM;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@JsonRootName(value = PROBLEM, namespace = NAMESPACE)
@JsonSerialize(converter = ConverterProblemToBridge.class)
@JsonDeserialize(converter = ConverterBridgeToProblem.class)
@JsonInclude(NON_EMPTY)
public interface ProblemJacksonMixIn {}
