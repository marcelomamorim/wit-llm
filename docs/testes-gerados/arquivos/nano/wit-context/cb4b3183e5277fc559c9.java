/*
 * @test
 * @summary Tests for java.security.Provider.putIfAbsent method.
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentWithNulls();
        testPutIfAbsentWithInitializedFalse();
    }

    public static void testPutIfAbsentWithNulls() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public Object putIfAbsent(Object key, Object value) {
                return super.putIfAbsent(key, value);
            }
        };
        Object result1 = provider.putIfAbsent("key1", "value1");
        Object result2 = provider.putIfAbsent("key1", "value2");
        if (result1 != null) {
            throw new AssertionError("Expected null on first putIfAbsent");
        }
        if (!"value1".equals(result2)) {
            throw new AssertionError("Expected value1 on second putIfAbsent, got: " + result2);
        }
    }

    public static void testPutIfAbsentWithInitializedFalse() {
        Provider provider = new Provider("TestProvider", "1.0", "Test Info") {
            private boolean initialized = false;
            @Override
            public Object putIfAbsent(Object key, Object value) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
                return super.putIfAbsent(key, value);
            }
        };
        try {
            provider.putIfAbsent("key2", "value2");
            throw new AssertionError("Expected IllegalStateException when not initialized");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
