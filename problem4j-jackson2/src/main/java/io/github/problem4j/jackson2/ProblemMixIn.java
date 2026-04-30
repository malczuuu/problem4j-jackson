/*
 * Copyright 2025-2026 The Problem4J Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.problem4j.jackson2;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static io.github.problem4j.jackson2.ProblemBridge.NAMESPACE;
import static io.github.problem4j.jackson2.ProblemBridge.PROBLEM;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Jackson MixIn that redirects serialization and deserialization of {@code Problem} through the
 * internal {@link ProblemBridge} POJO.
 *
 * @see io.github.problem4j.core.Problem
 * @since 1.3.0
 */
@JacksonXmlRootElement(localName = PROBLEM, namespace = NAMESPACE)
@JsonRootName(value = PROBLEM, namespace = NAMESPACE)
@JsonSerialize(converter = ConverterProblemToBridge.class)
@JsonDeserialize(converter = ConverterBridgeToProblem.class)
@JsonInclude(NON_EMPTY)
public interface ProblemMixIn {}
