package io.github.malczuuu.problem4j.jackson;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(converter = ConverterProblemToBridge.class)
@JsonDeserialize(converter = ConverterBridgeToProblem.class)
@JsonInclude(NON_EMPTY)
public interface ProblemMixIn {}
