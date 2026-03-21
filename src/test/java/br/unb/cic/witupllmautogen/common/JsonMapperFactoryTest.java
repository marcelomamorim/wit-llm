package br.unb.cic.witupllmautogen.common;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

class JsonMapperFactoryTest {

  @Test
  void shouldSerializeInstantAsIsoTextAndPrettyPrint() throws Exception {
    ObjectMapper mapper = JsonMapperFactory.createDefaultMapper();

    String json = mapper.writeValueAsString(Map.of("time", Instant.parse("2026-03-17T00:00:00Z")));

    assertTrue(json.contains("2026-03-17T00:00:00Z"));
    assertTrue(json.contains("\n"));
  }

  @Test
  void shouldThrowWhenInstantiatingUtilityClass() throws Exception {
    Constructor<JsonMapperFactory> constructor = JsonMapperFactory.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    InvocationTargetException exception =
        assertThrows(InvocationTargetException.class, constructor::newInstance);

    assertTrue(exception.getCause() instanceof UnsupportedOperationException);
  }
}
