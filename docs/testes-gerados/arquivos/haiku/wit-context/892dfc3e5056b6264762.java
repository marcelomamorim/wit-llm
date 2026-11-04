/*
 * @test
 * @summary WITUP-derived checks for Provider.forEach: visits entries of an initialized provider and rejects a null action with NullPointerException
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ProviderWitupTest {

    private static final class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "witup test provider");
        }
    }

    public static void main(String[] args) {
        testForEachVisitsEntries();
        testForEachNullActionThrowsNPE();
    }

    private static void testForEachVisitsEntries() {
        TestProvider p = new TestProvider();
        p.put("witup.key", "witup.value");
        final Map<Object, Object> seen = new HashMap<>();
        BiConsumer<Object, Object> action = (k, v) -> seen.put(k, v);
        p.forEach(action);
        if (!"witup.value".equals(seen.get("witup.key"))) {
            throw new AssertionError("forEach did not visit inserted entry");
        }
    }

    private static void testForEachNullActionThrowsNPE() {
        TestProvider p = new TestProvider();
        boolean thrown = false;
        try {
            p.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("forEach(null) did not throw NullPointerException");
        }
    }
}
