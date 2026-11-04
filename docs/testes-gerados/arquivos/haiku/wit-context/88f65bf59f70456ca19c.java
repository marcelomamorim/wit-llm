/*
 * @test
 * @summary Verifies that Provider.keySet() returns a readable, unmodifiable view of the provider's keys
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Set;

public class ProviderWitupTest {

    private static final class TestProvider extends Provider {
        private static final long serialVersionUID = 1L;

        TestProvider() {
            super("WitupTestProvider", "1.0", "test info");
        }
    }

    private static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();
        p.put("witupKey", "witupValue");

        Set<Object> keys = p.keySet();
        if (keys == null) {
            fail("keySet() returned null");
        }
        if (!keys.contains("witupKey")) {
            fail("keySet() does not contain the key added via put");
        }

        try {
            keys.add("witupAdded");
            fail("keySet().add() should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        try {
            keys.remove("witupKey");
            fail("keySet().remove() should throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        if (!p.keySet().contains("witupKey")) {
            fail("provider key was removed through keySet view");
        }
        if (p.keySet().contains("witupAdded")) {
            fail("provider key was added through keySet view");
        }
    }
}
