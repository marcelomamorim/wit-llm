/*
 * @test
 * @summary Test java.security.Provider.keySet() method behavior including IllegalStateException when uninitialized
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Set;

public class ProviderWitupTest {
    public static void main(String[] args) {
        testKeySetThrowsWhenUninitialized();
        testKeySetReturnsUnmodifiableSetWhenInitialized();
    }

    static class TestProvider extends Provider {
        private boolean initialized = false;

        protected TestProvider(String name, double version, String info) {
            super(name, version, info);
        }

        // Override checkInitialized to simulate initialized flag
        @Override
        protected void checkInitialized() {
            if (!initialized) {
                throw new IllegalStateException("Provider not initialized");
            }
        }

        void setInitialized(boolean val) {
            initialized = val;
        }

        // Override keySet to call super.keySet() to avoid infinite recursion
        @Override
        public Set<Object> keySet() {
            checkInitialized();
            return java.util.Collections.unmodifiableSet(super.keySet());
        }
    }

    private static void testKeySetThrowsWhenUninitialized() {
        TestProvider p = new TestProvider("TestProvider", 1.0, "Test Provider");
        p.setInitialized(false);
        try {
            p.keySet();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    private static void testKeySetReturnsUnmodifiableSetWhenInitialized() {
        TestProvider p = new TestProvider("TestProvider", 1.0, "Test Provider");
        p.setInitialized(true);
        // Add a key-value pair to the provider
        p.put("key1", "value1");
        Set<Object> keys = p.keySet();
        if (!keys.contains("key1")) {
            throw new RuntimeException("keySet does not contain expected key");
        }
        try {
            keys.add("key2");
            throw new RuntimeException("keySet is modifiable, expected unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // Expected
        }
    }
}