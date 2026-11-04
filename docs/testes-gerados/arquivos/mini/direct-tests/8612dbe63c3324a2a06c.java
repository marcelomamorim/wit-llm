/*
 * @test
 * @summary Test java.security.Provider.remove(Object key, Object value) method
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass of Provider to test remove method
        Provider p = new Provider("TestProvider", 1.0, "Test Provider Info") {
            private static final long serialVersionUID = 1L;
        };

        // Add a property to the provider
        p.put("key1", "value1");

        // Remove with matching key and value - should return true
        boolean removed = p.remove("key1", "value1");
        if (!removed) {
            throw new RuntimeException("Expected remove to return true when key and value match");
        }

        // Remove again with same key and value - should return false (already removed)
        boolean removedAgain = p.remove("key1", "value1");
        if (removedAgain) {
            throw new RuntimeException("Expected remove to return false when key is not present");
        }

        // Add property again
        p.put("key2", "value2");

        // Remove with matching key but different value - should return false
        boolean removedWrongValue = p.remove("key2", "wrongValue");
        if (removedWrongValue) {
            throw new RuntimeException("Expected remove to return false when value does not match");
        }

        // Remove with null key - should return false (no such key)
        boolean removedNullKey = p.remove(null, "value2");
        if (removedNullKey) {
            throw new RuntimeException("Expected remove to return false when key is null and not present");
        }

        // Remove with null value but existing key - should return false
        boolean removedNullValue = p.remove("key2", null);
        if (removedNullValue) {
            throw new RuntimeException("Expected remove to return false when value is null and does not match");
        }

        // Remove with both key and value null - should return false
        boolean removedNullBoth = p.remove(null, null);
        if (removedNullBoth) {
            throw new RuntimeException("Expected remove to return false when both key and value are null");
        }

        System.out.println("All tests passed.");
    }
}
