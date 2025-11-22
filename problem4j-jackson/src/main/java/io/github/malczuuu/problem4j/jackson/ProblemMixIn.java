package io.github.malczuuu.problem4j.jackson;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static io.github.malczuuu.problem4j.jackson.ProblemBridge.NAMESPACE;
import static io.github.malczuuu.problem4j.jackson.ProblemBridge.PROBLEM;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Jackson MixIn that redirects serialization and deserialization of {@code Problem} through the
 * {@link ProblemBridge} POJO.
 */
@JacksonXmlRootElement(localName = PROBLEM, namespace = NAMESPACE)
@JsonRootName(value = PROBLEM, namespace = NAMESPACE)
@JsonSerialize(converter = ConverterProblemToBridge.class)
@JsonDeserialize(converter = ConverterBridgeToProblem.class)
@JsonInclude(NON_EMPTY)
public interface ProblemMixIn {}
