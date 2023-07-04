/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package typeid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TypeIDTest {
  private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

  record Valid(String name, String prefix, String typeid, String uuid) {}

  record Invalid(String name, String typeid, String description) {}

  @ParameterizedTest
  @MethodSource("validParams")
  void valid(Valid valid) {
    var typeId = TypeID.fromString(valid.typeid()).get();
    assertEquals(valid.prefix(), typeId.prefix(), valid.name());
    assertEquals(valid.uuid(), typeId.uuid().toString());
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  void invalid(Invalid invalid) {
    assertEquals(Optional.empty(), TypeID.fromString(invalid.typeid()), invalid.description());
  }

  @Test
  void twoWay() {
    var in = new TypeID("test");
    assertEquals(Optional.of(in), TypeID.fromString(in.toString()));
  }

  static Stream<Valid> validParams() throws IOException {
    return MAPPER
        .readValue(
            TypeIDTest.class.getResourceAsStream("/valid.yml"), new TypeReference<List<Valid>>() {})
        .stream();
  }

  static Stream<Invalid> invalidParams() throws IOException {
    return MAPPER
        .readValue(
            TypeIDTest.class.getResourceAsStream("/invalid.yml"),
            new TypeReference<List<Invalid>>() {})
        .stream();
  }
}