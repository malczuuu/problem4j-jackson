/*
 * Copyright (c) 2025 Damian Malczewski
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * SPDX-License-Identifier: MIT
 */
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
 *
 * @deprecated use {@link io.github.problem4j.jackson2.ProblemMixIn}
 */
@Deprecated
@JacksonXmlRootElement(localName = PROBLEM, namespace = NAMESPACE)
@JsonRootName(value = PROBLEM, namespace = NAMESPACE)
@JsonSerialize(converter = ConverterProblemToBridge.class)
@JsonDeserialize(converter = ConverterBridgeToProblem.class)
@JsonInclude(NON_EMPTY)
public interface ProblemMixIn {}
