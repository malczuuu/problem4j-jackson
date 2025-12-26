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
package io.github.malczuuu.problem4j.jackson3;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static io.github.malczuuu.problem4j.jackson3.ProblemBridge.NAMESPACE;
import static io.github.malczuuu.problem4j.jackson3.ProblemBridge.PROBLEM;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/**
 * Jackson MixIn that redirects serialization and deserialization of {@code Problem} through the
 * {@link ProblemBridge} POJO.
 *
 * @deprecated migrated to {@code io.github.problem4j:problem4j-jackson3} namespace.
 */
@JsonRootName(value = PROBLEM, namespace = NAMESPACE)
@JsonSerialize(converter = ConverterProblemToBridge.class)
@JsonDeserialize(converter = ConverterBridgeToProblem.class)
@JsonInclude(NON_EMPTY)
@Deprecated(since = "1.2.6")
public interface ProblemJacksonMixIn {}
