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

/**
 * Jackson 3.x integration module for RFC 7807 Problem objects.
 *
 * <p>This package provides Jackson 3.x serialization and deserialization support for RFC 7807
 * Problem objects. It includes a custom {@code ProblemJacksonModule} that integrates with Jackson's
 * module system for seamless handling of Problem instances in JSON and XML formats.
 *
 * <p>The module is registered automatically via Jackson's module discovery mechanism when present
 * on the classpath.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7807">RFC 7807: Problem Details for HTTP APIs</a>
 * @see io.github.problem4j.jackson3.ProblemJacksonModule
 */
@NullMarked
package io.github.problem4j.jackson3;

import org.jspecify.annotations.NullMarked;
