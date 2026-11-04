/*
 * @test
 * @summary Verifies via reflection the public one-arg constructor signature of Sink.ChainedInt
 * @run main SinkWitupTest
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class SinkWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> sink = Class.forName("java.util.stream.Sink");
        Class<?> chained = Class.forName("java.util.stream.Sink$ChainedInt");

        if (!Modifier.isAbstract(chained.getModifiers())) {
            throw new RuntimeException("Sink.ChainedInt is expected to be abstract");
        }

        boolean found = false;
        for (Constructor<?> c : chained.getDeclaredConstructors()) {
            Class<?>[] params = c.getParameterTypes();
            if (params.length == 1 && params[0] == sink) {
                found = true;
                if (!Modifier.isPublic(c.getModifiers())) {
                    throw new RuntimeException("ChainedInt(Sink) constructor is not public");
                }
            }
        }
        if (!found) {
            throw new RuntimeException("ChainedInt(Sink) constructor not found");
        }
    }
}
