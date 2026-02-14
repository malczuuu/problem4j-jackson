/*
 * Copyright (c) 2025-2026 Damian Malczewski
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
package io.github.problem4j.jackson3;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static io.github.problem4j.jackson3.ProblemBridge.DETAIL;
import static io.github.problem4j.jackson3.ProblemBridge.INSTANCE;
import static io.github.problem4j.jackson3.ProblemBridge.STATUS;
import static io.github.problem4j.jackson3.ProblemBridge.TITLE;
import static io.github.problem4j.jackson3.ProblemBridge.TYPE;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.problem4j.core.Problem;
import io.github.problem4j.core.ProblemBuilder;
import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.JsonNode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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

  @Serial private static final long serialVersionUID = 1L;

  static final String PROBLEM = "problem";
  static final String NAMESPACE = "urn:ietf:rfc:7807";

  static final String TYPE = "type";
  static final String TITLE = "title";
  static final String STATUS = "status";
  static final String DETAIL = "detail";
  static final String INSTANCE = "instance";

  static final Set<String> MEMBERS = Set.of(TYPE, TITLE, STATUS, DETAIL, INSTANCE);

  private @Nullable URI type;
  private @Nullable String title;
  private int status = 0;
  private @Nullable String detail;
  private @Nullable URI instance;
  private final Map<String, Object> extensions = new TreeMap<>();

  ProblemBridge() {}

  ProblemBridge(Problem problem) {
    type = problem.isTypeNonBlank() ? problem.getType() : null;
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
        .extensions(extensions);
  }

  @JsonProperty(TYPE)
  void setType(@Nullable JsonNode node) {
    if (node != null && node.isString()) {
      try {
        this.type = URI.create(node.asString());
      } catch (IllegalArgumentException e) {
        this.type = null;
      }
    }
  }

  @JsonProperty(TITLE)
  void setTitle(@Nullable String title) {
    this.title = title;
  }

  @JsonProperty(STATUS)
  void setStatus(@Nullable JsonNode node) {
    if (node != null) {
      if (node.isIntegralNumber()) {
        this.status = node.intValue();
      } else {
        try {
          this.status = Integer.parseInt(node.asString());
        } catch (NumberFormatException ignored) {
          this.status = 0;
        }
      }
    }
  }

  @JsonProperty(DETAIL)
  void setDetail(@Nullable String detail) {
    this.detail = detail;
  }

  @JsonProperty(INSTANCE)
  void setInstance(@Nullable JsonNode node) {
    if (node != null && node.isString()) {
      try {
        this.instance = URI.create(node.asString());
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
   * <p>Warnings are suppressed for unchecked operations on raw types, as full knowledge about types
   * of extension values is not available at this point.
   *
   * @param name the JSON property name
   * @param value the JSON property value
   */
  @JsonAnySetter
  @SuppressWarnings({"unchecked", "rawtypes"})
  void setExtension(@Nullable String name, @Nullable Object value) {
    if (name != null && value != null && !MEMBERS.contains(name)) {
      if (!extensions.containsKey(name)) {
        extensions.put(name, value);
      } else if (extensions.get(name) instanceof List) {
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
  @Nullable String getType() {
    return type != null ? type.toString() : null;
  }

  @JsonProperty(TITLE)
  @JacksonXmlProperty(namespace = NAMESPACE, localName = TITLE)
  @Nullable String getTitle() {
    return title;
  }

  @JsonProperty(STATUS)
  @JacksonXmlProperty(namespace = NAMESPACE, localName = STATUS)
  int getStatus() {
    return status;
  }

  @JsonProperty(DETAIL)
  @JacksonXmlProperty(namespace = NAMESPACE, localName = DETAIL)
  @Nullable String getDetail() {
    return detail;
  }

  @JsonProperty(INSTANCE)
  @JacksonXmlProperty(namespace = NAMESPACE, localName = INSTANCE)
  @Nullable String getInstance() {
    return instance != null ? instance.toString() : null;
  }

  @JsonAnyGetter
  @JacksonXmlProperty(namespace = NAMESPACE)
  Map<String, Object> getExtensions() {
    return extensions;
  }
}
