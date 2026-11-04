/*
 * @test
 * @summary Test the replace method of java.security.Provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testReplaceReturnsPreviousValue();
        testReplaceWithNullKey();
        testReplaceWithNullValue();
    }

    private static void testReplaceReturnsPreviousValue() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        Object key = "testKey";
        Object initialValue = "initialValue";
        Object newValue = "newValue";
        // Use implReplace directly for setup, assuming it's accessible or simulate via replace
        // Since implReplace is not public, we simulate the effect by calling replace with initial value
        provider.replace(key, initialValue);
        Object previous = provider.replace(key, newValue);
        // The previous value should be initialValue
        if (previous == null || !previous.equals(initialValue)) {
            throw new AssertionError("Expected previous value to be initialValue, but got: " + previous);
        }
    }

    private static void testReplaceWithNullKey() {
        Provider provider = new Provider("TestProvider", "1.0", "Test Info") {};
        Object previous = provider.replace(null, "value");
        // Expect the method to handle null key gracefully, possibly returning null
        if (previous != null) {
            throw new AssertionError("Expected null previous value for null key, but got: " + previous);
        }
    }

    private static void testReplaceWithNullValue() {
        Provider provider = new Provider("TestProvider", "1.0", "Test Info") {};
        Object key = "testKey";
        Object previous = provider.replace(key, null);
        // The previous value should be null if key was not set before
        // If key was set before, previous could be non-null; for test, assume initial null
        if (previous != null) {
            throw new AssertionError("Expected null previous value for null replacement, but got: " + previous);
        }
    }
}
