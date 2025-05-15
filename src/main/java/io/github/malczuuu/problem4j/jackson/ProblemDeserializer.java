package io.github.malczuuu.problem4j.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemBuilder;
import java.io.IOException;
import java.io.Serial;
import java.net.URI;
import java.util.Iterator;

class ProblemDeserializer extends StdDeserializer<Problem> {

  @Serial private static final long serialVersionUID = 1L;

  ProblemDeserializer() {
    super(Problem.class);
  }

  @Override
  public Problem deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    ProblemBuilder builder = Problem.builder();

    Iterator<String> fieldNames = node.fieldNames();
    while (fieldNames.hasNext()) {
      String field = fieldNames.next();
      switch (field) {
        case ProblemMember.TYPE:
          builder.type(URI.create(node.get(ProblemMember.TYPE).textValue()));
          break;
        case ProblemMember.TITLE:
          builder.title(node.get(ProblemMember.TITLE).textValue());
          break;
        case ProblemMember.STATUS:
          builder.status(node.get(ProblemMember.STATUS).intValue());
          break;
        case ProblemMember.DETAIL:
          builder.detail(node.get(ProblemMember.DETAIL).textValue());
          break;
        case ProblemMember.INSTANCE:
          builder.instance(URI.create(node.get(ProblemMember.INSTANCE).textValue()));
          break;
        default:
          if (jsonParser.getCodec() instanceof ObjectMapper mapper) {
            builder.extension(field, mapper.treeToValue(node.get(field), Object.class));
          }
          break;
      }
    }
    return builder.build();
  }
}
