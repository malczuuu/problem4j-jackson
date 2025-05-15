package io.github.malczuuu.problem4j.jackson;

import java.util.Set;

class ProblemMember {

  static final String TYPE = "type";
  static final String TITLE = "title";
  static final String STATUS = "status";
  static final String DETAIL = "detail";
  static final String INSTANCE = "instance";

  static final Set<String> PROBLEM_MEMBERS = Set.of(TYPE, TITLE, STATUS, DETAIL, INSTANCE);
}
