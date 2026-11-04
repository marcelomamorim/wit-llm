/*
 * @test
 * @summary Test java.security.Provider.getProperty(String) for initialized state and property retrieval
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) {
        testGetPropertyThrowsIfNotInitialized();
        testGetPropertyReturnsValueWhenInitialized();
    }

    private static void testGetPropertyThrowsIfNotInitialized() {
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            // Override checkInitialized to simulate not initialized state
            @Override
            void checkInitialized() {
                throw new IllegalStateException("Provider not initialized");
            }
        };

        try {
            p.getProperty("anyKey");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
            if (!"Provider not initialized".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testGetPropertyReturnsValueWhenInitialized() {
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            @Override
            void checkInitialized() {
                // Do nothing, simulate initialized
            }
        };

        // Put a property in the Provider's properties map
        p.put("key1", "value1");

        String value = p.getProperty("key1");
        if (!"value1".equals(value)) {
            throw new RuntimeException("Expected value1 but got: " + value);
        }

        // Test for a key that does not exist returns null
        String nullValue = p.getProperty("nonexistent");
        if (nullValue != null) {
            throw new RuntimeException("Expected null for nonexistent key but got: " + nullValue);
        }
    }
}
