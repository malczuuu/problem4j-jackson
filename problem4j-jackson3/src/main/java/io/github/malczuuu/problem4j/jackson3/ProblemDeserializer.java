package io.github.malczuuu.problem4j.jackson3;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemBuilder;
import java.net.URI;
import java.util.Collection;
import java.util.Map;
import tools.jackson.core.JsonParser;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/** Jackson deserializer for {@link Problem}. */
class ProblemDeserializer extends StdDeserializer<Problem> {

  ProblemDeserializer() {
    super(Problem.class);
  }

  /**
   * Method that deserializes JSON content into a {@link Problem} instance. It does so by reading
   * the JSON content into a Map and then applying the map entries to a {@link ProblemBuilder}.
   */
  @Override
  public Problem deserialize(JsonParser jsonParser, DeserializationContext context) {
    Map<String, Object> node = jsonParser.readValueAs(new TypeReference<>() {});

    ProblemBuilder builder = Problem.builder();

    Collection<String> propertyNames = node.keySet();
    for (String property : propertyNames.stream().sorted().toList()) {
      if (node.get(property) != null) {
        apply(builder, property, node.get(property));
      }
    }
    return builder.build();
  }

  private void apply(ProblemBuilder builder, String property, Object value) {
    switch (property) {
      case ProblemMember.TYPE:
        builder.type(parseUri(value));
        break;
      case ProblemMember.TITLE:
        builder.title(value.toString());
        break;
      case ProblemMember.STATUS:
        builder.status(parseStatus(value));
        break;
      case ProblemMember.DETAIL:
        builder.detail(value.toString());
        break;
      case ProblemMember.INSTANCE:
        builder.instance(parseUri(value));
        break;
      default:
        builder.extension(property, value);
        break;
    }
  }

  private URI parseUri(Object node) {
    String valueAsString = node.toString();
    if (valueAsString == null) {
      return null;
    }
    try {
      return URI.create(valueAsString);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  private int parseStatus(Object value) {
    return value instanceof Number number ? number.intValue() : 0;
  }
}
