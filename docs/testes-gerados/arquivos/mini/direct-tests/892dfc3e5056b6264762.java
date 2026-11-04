/*
 * @test
 * @summary Test java.security.Provider.forEach method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.function.BiConsumer;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithNullAction();
    }

    private static void testForEachWithEntries() {
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private static final long serialVersionUID = 1L;
        };

        // Put some entries
        p.put("key1", "value1");
        p.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();

        BiConsumer<Object, Object> action = new BiConsumer<Object, Object>() {
            @Override
            public void accept(Object k, Object v) {
                sb.append(k).append("=").append(v).append(",");
            }
        };

        p.forEach(action);

        String result = sb.toString();

        // Check that both entries were processed
        if (!(result.contains("key1=value1") && result.contains("key2=value2"))) {
            throw new AssertionError("forEach did not process all entries: " + result);
        }
    }

    private static void testForEachWithNullAction() {
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private static final long serialVersionUID = 1L;
        };

        boolean threw = false;
        try {
            p.forEach(null);
        } catch (NullPointerException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("forEach should throw NullPointerException when action is null");
        }
    }
}
