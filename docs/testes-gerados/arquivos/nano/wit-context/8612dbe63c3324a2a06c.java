/*
 * @test
 * @summary Tests the remove method of java.security.Provider for expected behavior.
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullKeyAndValue();
        testRemoveWhenNotInitialized();
    }

    public static void testRemoveWithNullKeyAndValue() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public synchronized boolean implRemove(Object key, Object value) {
                // Simulate successful removal
                return true;
            }
        };
        try {
            boolean result = provider.remove(null, null);
            if (result != true) {
                throw new AssertionError("Expected true when removing null key and value, got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    public static void testRemoveWhenNotInitialized() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;
            @Override
            public synchronized boolean implRemove(Object key, Object value) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
                return true;
            }
        };
        try {
            provider.remove("someKey", "someValue");
            throw new AssertionError("Expected IllegalStateException when provider not initialized");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
