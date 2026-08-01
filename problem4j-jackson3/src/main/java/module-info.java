/*
 * Copyright 2025-present the original author or authors.
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

import org.jspecify.annotations.NullMarked;

/**
 * Jackson 3.x integration module for RFC 7807 Problem objects.
 *
 * <p>This module provides Jackson 3.x integration for serializing and deserializing RFC 7807
 * Problem objects. It declares optional dependencies on Jackson's databind and XML dataformat
 * modules, making them optional for module resolution while supporting both JSON and XML
 * serialization.
 *
 * <p>The module exports the {@code io.github.problem4j.jackson3} package and provides the {@code
 * ProblemJacksonModule} as a Jackson module service.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7807">RFC 7807: Problem Details for HTTP APIs</a>
 * @see io.github.problem4j.jackson3.ProblemJacksonModule
 */
@NullMarked
module io.github.problem4j.jackson3 {
  requires static org.jspecify;
  requires static tools.jackson.databind;
  requires static tools.jackson.dataformat.xml;
  requires transitive io.github.problem4j.core;

  exports io.github.problem4j.jackson3;

  // opens the package to Jackson's databind module for reflection-based ser/des, to allow Jackson
  // to access non-public members of classes in this package, which is necessary, as custom
  // converters and bridge POJO are package-private
  opens io.github.problem4j.jackson3 to
      tools.jackson.databind;

  provides tools.jackson.databind.JacksonModule with
      io.github.problem4j.jackson3.ProblemJacksonModule;
}
