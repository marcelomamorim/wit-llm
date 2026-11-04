/*
 * @test
 * @summary Test java.security.Provider.keys() method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Enumeration;

public class ProviderWitupTest extends Provider {

    private boolean initialized = false;

    protected ProviderWitupTest() {
        super("TestProvider", 1.0, "Test Provider for keys() method");
    }

    // Override checkInitialized to control initialization state
    void checkInitialized() {
        if (!initialized) {
            throw new IllegalStateException("Provider not initialized");
        }
    }

    // Expose method to set initialized flag
    void setInitialized(boolean val) {
        initialized = val;
    }

    public static void main(String[] args) {
        ProviderWitupTest p = new ProviderWitupTest();

        // Test 1: keys() should throw IllegalStateException if not initialized
        try {
            p.keys();
            throw new RuntimeException("keys() did not throw IllegalStateException when not initialized");
        } catch (IllegalStateException e) {
            // Expected
        }

        // Test 2: keys() should return empty enumeration when initialized and no keys added
        p.setInitialized(true);
        Enumeration<Object> keys = p.keys();
        if (keys == null) {
            throw new RuntimeException("keys() returned null enumeration");
        }
        if (keys.hasMoreElements()) {
            throw new RuntimeException("keys() returned non-empty enumeration when no keys added");
        }

        // Test 3: keys() should return added keys
        p.put("key1", "value1");
        p.put("key2", "value2");
        Enumeration<Object> keys2 = p.keys();
        boolean foundKey1 = false;
        boolean foundKey2 = false;
        while (keys2.hasMoreElements()) {
            Object k = keys2.nextElement();
            if ("key1".equals(k)) {
                foundKey1 = true;
            } else if ("key2".equals(k)) {
                foundKey2 = true;
            }
        }
        if (!foundKey1 || !foundKey2) {
            throw new RuntimeException("keys() did not return all added keys");
        }

        System.out.println("All tests passed.");
    }
}
