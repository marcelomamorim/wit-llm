/*
 * @test
 * @summary Test java.security.Provider.putIfAbsent behavior including IllegalStateException when uninitialized
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        testPutIfAbsentThrowsWhenUninitialized();
        testPutIfAbsentReturnsNullWhenKeyAbsent();
        testPutIfAbsentReturnsOldValueWhenKeyPresent();
    }

    static class TestProvider extends Provider {
        private boolean initialized = true;

        protected TestProvider(String name, double version, String info) {
            super(name, version, info);
        }

        // Override check to simulate initialized state
        @Override
        protected void check(String service) {
            if (!initialized) {
                throw new IllegalStateException("Provider not initialized");
            }
        }

        // Expose method to set initialized flag
        void setInitialized(boolean state) {
            this.initialized = state;
        }

        // Expose implPutIfAbsent for testing
        synchronized Object implPutIfAbsent(Object key, Object value) {
            return super.putIfAbsent(key, value);
        }
    }

    private static void testPutIfAbsentThrowsWhenUninitialized() {
        TestProvider p = new TestProvider("TestProvider", 1.0, "Test Provider");
        p.setInitialized(false);
        try {
            p.putIfAbsent("key", "value");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    private static void testPutIfAbsentReturnsNullWhenKeyAbsent() {
        TestProvider p = new TestProvider("TestProvider", 1.0, "Test Provider");
        p.setInitialized(true);
        Object oldValue = p.putIfAbsent("uniqueKey", "value1");
        if (oldValue != null) {
            throw new RuntimeException("Expected null when key absent, got: " + oldValue);
        }
    }

    private static void testPutIfAbsentReturnsOldValueWhenKeyPresent() {
        TestProvider p = new TestProvider("TestProvider", 1.0, "Test Provider");
        p.setInitialized(true);
        p.putIfAbsent("dupKey", "value1");
        Object oldValue = p.putIfAbsent("dupKey", "value2");
        if (!"value1".equals(oldValue)) {
            throw new RuntimeException("Expected old value 'value1', got: " + oldValue);
        }
    }
}
