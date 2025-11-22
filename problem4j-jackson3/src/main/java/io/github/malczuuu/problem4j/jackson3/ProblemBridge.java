package io.github.malczuuu.problem4j.jackson3;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemBuilder;
import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import tools.jackson.databind.JsonNode;

/**
 * Bridge POJO used to serialize and deserialize {@link Problem} instances. It provides a concrete,
 * Jackson-friendly representation of the otherwise interface-based Problem model. During
 * serialization the original Problem is mapped into this class, and during deserialization this
 * class is converted back into a full Problem instance using the appropriate builder or factory.
 * This indirection ensures consistent JSON/XML output and full control over extension members and
 * namespaces.
 */
@JsonPropertyOrder({
  ProblemMember.TYPE,
  ProblemMember.TITLE,
  ProblemMember.STATUS,
  ProblemMember.DETAIL,
  ProblemMember.INSTANCE
})
@JsonInclude(NON_EMPTY)
class ProblemBridge implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

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

  @JsonProperty(ProblemMember.TYPE)
  void setType(JsonNode node) {
    if (node != null && node.isString()) {
      try {
        this.type = URI.create(node.asString());
      } catch (IllegalArgumentException e) {
        this.type = null;
      }
    }
  }

  @JsonProperty(ProblemMember.TITLE)
  void setTitle(String title) {
    this.title = title;
  }

  @JsonProperty(ProblemMember.STATUS)
  void setStatus(JsonNode node) {
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

  @JsonProperty(ProblemMember.DETAIL)
  void setDetail(String detail) {
    this.detail = detail;
  }

  @JsonProperty(ProblemMember.INSTANCE)
  void setInstance(JsonNode node) {
    if (node != null && node.isString()) {
      try {
        this.instance = URI.create(node.asString());
      } catch (IllegalArgumentException e) {
        this.instance = null;
      }
    }
  }

  @JsonAnySetter
  void setExtension(String name, Object value) {
    if (name != null && value != null && !ProblemMember.PROBLEM_MEMBERS.contains(name)) {
      if (!extensions.containsKey(name)) {
        extensions.put(name, value);
      } else if (extensions.get(name) instanceof List) {
        // full knowledge about types of these list items is irrelevant
        // noinspection unchecked,rawtypes
        ((List) extensions.get(name)).add(value);
      } else {
        List<Object> list = new ArrayList<>();
        list.add(extensions.get(name));
        list.add(value);
        extensions.put(name, list);
      }
    }
  }

  @JsonProperty(ProblemMember.TYPE)
  String getType() {
    return type != null ? type.toString() : null;
  }

  @JsonProperty(ProblemMember.TITLE)
  String getTitle() {
    return title;
  }

  @JsonProperty(ProblemMember.STATUS)
  int getStatus() {
    return status;
  }

  @JsonProperty(ProblemMember.DETAIL)
  String getDetail() {
    return detail;
  }

  @JsonProperty(ProblemMember.INSTANCE)
  String getInstance() {
    return instance != null ? instance.toString() : null;
  }

  @JsonAnyGetter
  Map<String, Object> getExtensions() {
    return extensions;
  }
}
