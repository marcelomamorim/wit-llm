/*
 * @test
 * @summary Verify Properties.addAll(Collection) always throws UnsupportedOperationException and leaves the properties unchanged
 * @run main PropertiesWitupTest
 */
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties p = new Properties();

        Collection<Map.Entry<Object, Object>> empty = Collections.<Map.Entry<Object, Object>>emptyList();
        expectUnsupported(p, empty);

        Collection<Map.Entry<Object, Object>> one =
            Collections.<Map.Entry<Object, Object>>singletonList(
                new AbstractMap.SimpleEntry<Object, Object>("k", "v"));
        expectUnsupported(p, one);

        if (p.size() != 0) {
            throw new RuntimeException("size changed after addAll: " + p.size());
        }
        if (!p.isEmpty()) {
            throw new RuntimeException("properties not empty after addAll");
        }
        if (p.getProperty("k") != null) {
            throw new RuntimeException("entry was added by addAll");
        }
    }

    private static void expectUnsupported(Properties p,
                                          Collection<Map.Entry<Object, Object>> c) {
        try {
            p.addAll(c);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
