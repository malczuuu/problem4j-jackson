package io.github.malczuuu.problem4j.jackson3;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(converter = ConverterProblemToBridge.class)
@JsonDeserialize(converter = ConverterBridgeToProblem.class)
@JsonInclude(NON_EMPTY)
public interface ProblemMixIn {}
