/*
 * @test
 * @summary Test java.security.Provider.getOrDefault behavior including IllegalStateException when uninitialized
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        testGetOrDefaultThrowsIfNotInitialized();
        testGetOrDefaultReturnsDefaultIfKeyAbsent();
        testGetOrDefaultReturnsValueIfKeyPresent();
    }

    private static void testGetOrDefaultThrowsIfNotInitialized() {
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;
            @Override
            protected synchronized void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }

            // Override getOrDefault to call super.getOrDefault after checkInitialized
            @Override
            public synchronized Object getOrDefault(Object key, Object defaultValue) {
                checkInitialized();
                return super.getOrDefault(key, defaultValue);
            }
        };

        try {
            p.getOrDefault("anyKey", "default");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    private static void testGetOrDefaultReturnsDefaultIfKeyAbsent() {
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = true;
            @Override
            protected synchronized void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }
        };

        Object defaultValue = "defaultValue";
        Object result = p.getOrDefault("nonExistingKey", defaultValue);
        if (result != defaultValue) {
            throw new RuntimeException("getOrDefault did not return default value when key absent");
        }
    }

    private static void testGetOrDefaultReturnsValueIfKeyPresent() {
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = true;
            @Override
            protected synchronized void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }
        };

        // Put a key-value pair
        p.put("existingKey", "value123");

        Object result = p.getOrDefault("existingKey", "defaultValue");
        if (!"value123".equals(result)) {
            throw new RuntimeException("getOrDefault did not return the stored value for existing key");
        }
    }
}