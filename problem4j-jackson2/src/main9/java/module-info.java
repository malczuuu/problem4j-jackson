/*
 * Copyright (c) 2025-2026 The Problem4J Authors
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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import org.jspecify.annotations.NullMarked;

/**
 * Jackson 2.x integration module for RFC 7807 Problem objects.
 *
 * <p>This module provides Jackson 2.x integration for serializing and deserializing RFC 7807
 * Problem objects. It declares optional dependencies on Jackson's databind and XML dataformat
 * modules, making them optional for module resolution while supporting both JSON and XML
 * serialization.
 *
 * <p>The module exports the {@code io.github.problem4j.jackson2} package and provides the {@code
 * ProblemModule} as a Jackson module service.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7807">RFC 7807: Problem Details for HTTP APIs</a>
 * @see io.github.problem4j.jackson2.ProblemModule
 */
@NullMarked
module io.github.problem4j.jackson2 {
  requires static com.fasterxml.jackson.databind;
  requires static com.fasterxml.jackson.dataformat.xml;
  requires static org.jspecify;
  requires transitive io.github.problem4j.core;

  exports io.github.problem4j.jackson2;

  // opens the package to Jackson's databind module for reflection-based ser/des, to allow Jackson
  // to access non-public members of classes in this package, which is necessary, as custom
  // converters and bridge POJO are package-private
  opens io.github.problem4j.jackson2 to
      com.fasterxml.jackson.databind;

  provides com.fasterxml.jackson.databind.Module with
      io.github.problem4j.jackson2.ProblemModule;
}
