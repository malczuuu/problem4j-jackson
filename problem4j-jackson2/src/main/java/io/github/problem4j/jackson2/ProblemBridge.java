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
package io.github.problem4j.jackson2;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static io.github.problem4j.jackson2.ProblemBridge.DETAIL;
import static io.github.problem4j.jackson2.ProblemBridge.INSTANCE;
import static io.github.problem4j.jackson2.ProblemBridge.STATUS;
import static io.github.problem4j.jackson2.ProblemBridge.TITLE;
import static io.github.problem4j.jackson2.ProblemBridge.TYPE;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.problem4j.core.Problem;
import io.github.problem4j.core.ProblemBuilder;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Bridge POJO used to serialize and deserialize {@link Problem} instances. It provides a concrete,
 * Jackson-friendly representation of the otherwise interface-based Problem model. During
 * serialization the original Problem is mapped into this class, and during deserialization this
 * class is converted back into a full Problem instance using the appropriate builder or factory.
 * This indirection ensures consistent JSON/XML output and full control over extension members and
 * namespaces.
 */
@JsonPropertyOrder({TYPE, TITLE, STATUS, DETAIL, INSTANCE})
@JsonInclude(NON_EMPTY)
class ProblemBridge implements Serializable {

  private static final long serialVersionUID = 1L;

  static final String NAMESPACE = "urn:ietf:rfc:7807";
  static final String PROBLEM = "problem";

  static final String TYPE = "type";
  static final String TITLE = "title";
  static final String STATUS = "status";
  static final String DETAIL = "detail";
  static final String INSTANCE = "instance";

  static final Set<String> PROBLEM_MEMBERS = setOf(TYPE, TITLE, STATUS, DETAIL, INSTANCE);

  private URI type;
  private String title;
  private int status = 0;
  private String detail;
  private URI instance;
  private final Map<String, Object> extensions = new TreeMap<>();

  ProblemBridge() {}

  ProblemBridge(Problem problem) {
    type =
        problem.getType() != null && !problem.getType().equals(Problem.BLANK_TYPE)
            ? problem.getType()
            : null;
    title = problem.getTitle();
    status = problem.getStatus();
    detail = problem.getDetail();
    instance = problem.getInstance() != null ? problem.getInstance() : null;

    problem.getExtensionMembers().forEach(this::setExtension);
  }

  ProblemBuilder toProblemBuilder() {
    return Problem.builder()
        .type(type)
        .title(title)
        .status(status)
        .detail(detail)
        .instance(instance)
        .extension(extensions);
  }

  @JsonProperty(TYPE)
  void setType(JsonNode node) {
    if (node != null && node.isTextual()) {
      try {
        this.type = URI.create(node.asText());
      } catch (IllegalArgumentException e) {
        this.type = null;
      }
    }
  }

  @JsonProperty(TITLE)
  void setTitle(String title) {
    this.title = title;
  }

  @JsonProperty(STATUS)
  void setStatus(JsonNode node) {
    // boolean values excluded because true is returned as 1
    if (node != null && !node.isBoolean()) {
      this.status = node.asInt();
    }
  }

  @JsonProperty(DETAIL)
  void setDetail(String detail) {
    this.detail = detail;
  }

  @JsonProperty(INSTANCE)
  void setInstance(JsonNode node) {
    if (node != null && node.isTextual()) {
      try {
        this.instance = URI.create(node.asText());
      } catch (IllegalArgumentException e) {
        this.instance = null;
      }
    }
  }

  /**
   * Collects non-standard JSON properties as {@link Problem} extensions.
   *
   * <p>This method is invoked by Jackson for any JSON property that is not mapped to a known
   * member. Reserved problem members are ignored.
   *
   * <p>If the same extension name occurs multiple times, values are merged:
   *
   * <ul>
   *   <li>a single value is stored as-is,
   *   <li>multiple values are accumulated into a {@link List}.
   * </ul>
   *
   * @param name the JSON property name
   * @param value the JSON property value
   */
  @JsonAnySetter
  void setExtension(String name, Object value) {
    if (name != null && value != null && !PROBLEM_MEMBERS.contains(name)) {
      if (!extensions.containsKey(name)) {
        extensions.put(name, value);
      } else if (extensions.get(name) instanceof List) {
        // full knowledge about types of these list items is irrelevant
        ((List) extensions.get(name)).add(value);
      } else {
        List<Object> list = new ArrayList<>();
        list.add(extensions.get(name));
        list.add(value);
        extensions.put(name, list);
      }
    }
  }

  @JsonProperty(TYPE)
  @JacksonXmlProperty(namespace = NAMESPACE, localName = TYPE)
  String getType() {
    return type != null ? type.toString() : null;
  }

  @JsonProperty(TITLE)
  @JacksonXmlProperty(namespace = NAMESPACE, localName = TITLE)
  String getTitle() {
    return title;
  }

  @JsonProperty(STATUS)
  @JacksonXmlProperty(namespace = NAMESPACE, localName = STATUS)
  int getStatus() {
    return status;
  }

  @JsonProperty(DETAIL)
  @JacksonXmlProperty(namespace = NAMESPACE, localName = DETAIL)
  String getDetail() {
    return detail;
  }

  @JsonProperty(INSTANCE)
  @JacksonXmlProperty(namespace = NAMESPACE, localName = INSTANCE)
  String getInstance() {
    return instance != null ? instance.toString() : null;
  }

  @JsonAnyGetter
  @JacksonXmlProperty(namespace = NAMESPACE)
  Map<String, Object> getExtensions() {
    return extensions;
  }

  /** Simulate {@code Set.of} from higher Java versions. */
  private static Set<String> setOf(String... args) {
    return new HashSet<>(Arrays.asList(args));
  }
}
