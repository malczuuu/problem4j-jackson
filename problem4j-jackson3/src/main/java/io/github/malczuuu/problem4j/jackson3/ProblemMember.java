package io.github.malczuuu.problem4j.jackson3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Constants for {@code Problem} field names.
 *
 * @see io.github.malczuuu.problem4j.core.Problem
 */
class ProblemMember {

  static final String TYPE = "type";
  static final String TITLE = "title";
  static final String STATUS = "status";
  static final String DETAIL = "detail";
  static final String INSTANCE = "instance";

  static final Set<String> PROBLEM_MEMBERS =
      new HashSet<>(Arrays.asList(TYPE, TITLE, STATUS, DETAIL, INSTANCE));
}
