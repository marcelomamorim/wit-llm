/* @test    @summary Tests the keySet() method of java.security.Provider for normal and exceptional behavior    @run main ProviderWitupTest */

import java.security.Provider;
import java.util.Set;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testKeySetWhenInitialized();
        testKeySetWhenNotInitialized();
    }

    private static void testKeySetWhenInitialized() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = true;
            protected void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Not initialized");
                }
            }
        };
        try {
            Set<Object> keys = provider.keySet();
            if (keys == null) {
                throw new AssertionError("Expected non-null key set when initialized");
            }
        } catch (IllegalStateException e) {
            throw new AssertionError("Did not expect IllegalStateException when initialized", e);
        }
    }

    private static void testKeySetWhenNotInitialized() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            private boolean initialized = false;
            protected void checkInitialized() {
                if (!initialized) {
                    throw new IllegalStateException("Not initialized")
                }
            }
        };
        try {
            provider.keySet();
            throw new AssertionError("Expected IllegalStateException when not initialized");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
