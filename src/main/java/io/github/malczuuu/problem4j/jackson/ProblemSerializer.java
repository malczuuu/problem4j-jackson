package io.github.malczuuu.problem4j.jackson;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.malczuuu.problem4j.core.Problem;
import java.io.IOException;

class ProblemSerializer extends StdSerializer<Problem> {

  private static final long serialVersionUID = 1L;

  ProblemSerializer() {
    super(Problem.class);
  }

  @Override
  public void serialize(Problem problem, JsonGenerator jsonGenerator, SerializerProvider provider)
      throws IOException {
    jsonGenerator.writeStartObject();
    if (problem.getType() != null) {
      jsonGenerator.writeStringField(ProblemMember.TYPE, problem.getType().toString());
    } else {
      jsonGenerator.writeStringField(ProblemMember.TYPE, Problem.BLANK_TYPE.toString());
    }
    if (problem.getTitle() != null) {
      jsonGenerator.writeStringField(ProblemMember.TITLE, problem.getTitle());
    }
    jsonGenerator.writeNumberField(ProblemMember.STATUS, problem.getStatus());
    if (problem.getDetail() != null) {
      jsonGenerator.writeStringField(ProblemMember.DETAIL, problem.getDetail());
    }
    if (problem.getInstance() != null) {
      jsonGenerator.writeStringField(ProblemMember.INSTANCE, problem.getInstance().toString());
    }
    for (String extension : problem.getExtensions().stream().sorted().collect(toList())) {
      writeExtension(problem, jsonGenerator, extension);
    }
    jsonGenerator.writeEndObject();
  }

  /** If extension member name overlaps with native problem details members, then it is ignored. */
  private void writeExtension(Problem problem, JsonGenerator jsonGenerator, String extension)
      throws IOException {
    if (extension != null
        && !ProblemMember.PROBLEM_MEMBERS.contains(extension)
        && problem.getExtensionValue(extension) != null) {
      jsonGenerator.writeObjectField(extension, problem.getExtensionValue(extension));
    }
  }
}
