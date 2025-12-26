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

import io.github.malczuuu.problem4j.core.Problem;
import tools.jackson.databind.util.StdConverter;

/**
 * @deprecated migrated to {@code io.github.problem4j:problem4j-jackson3} namespace.
 */
@Deprecated(since = "1.2.6")
class ConverterProblemToBridge extends StdConverter<Problem, ProblemBridge> {

  @Override
  public ProblemBridge convert(Problem value) {
    return new ProblemBridge(value);
  }
}
