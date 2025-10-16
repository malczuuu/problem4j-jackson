package io.github.malczuuu.problem4j.jackson3;

import io.github.malczuuu.problem4j.core.Problem;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/** Jackson serializer for {@link Problem}. */
class ProblemSerializer extends StdSerializer<Problem> {

  ProblemSerializer() {
    super(Problem.class);
  }

  /**
   * Method that serializes {@link Problem} instance into JSON content. It does so by writing
   * problem details members and extensions into JSON using {@link JsonGenerator}.
   */
  @Override
  public void serialize(
      Problem problem, JsonGenerator jsonGenerator, SerializationContext provider) {
    jsonGenerator.writeStartObject();
    if (problem.getType() != null && !Problem.BLANK_TYPE.equals(problem.getType())) {
      jsonGenerator.writeStringProperty(ProblemMember.TYPE, problem.getType().toString());
    }
    if (problem.getTitle() != null) {
      jsonGenerator.writeStringProperty(ProblemMember.TITLE, problem.getTitle());
    }
    jsonGenerator.writeNumberProperty(ProblemMember.STATUS, problem.getStatus());
    if (problem.getDetail() != null) {
      jsonGenerator.writeStringProperty(ProblemMember.DETAIL, problem.getDetail());
    }
    if (problem.getInstance() != null) {
      jsonGenerator.writeStringProperty(ProblemMember.INSTANCE, problem.getInstance().toString());
    }
    for (String extension : problem.getExtensions().stream().sorted().toList()) {
      writeExtension(problem, jsonGenerator, extension);
    }
    jsonGenerator.writeEndObject();
  }

  /** If extension member name overlaps with native problem details members, then it is ignored. */
  private void writeExtension(Problem problem, JsonGenerator jsonGenerator, String extension) {
    if (extension != null
        && !ProblemMember.PROBLEM_MEMBERS.contains(extension)
        && problem.getExtensionValue(extension) != null) {
      jsonGenerator.writePOJOProperty(extension, problem.getExtensionValue(extension));
    }
  }
}
