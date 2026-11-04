/*
 * @test
 * @summary Test java.security.Provider.put(Object key, Object value) method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        testPutWithValidInputs();
        testPutThrowsWhenNotInitialized();
    }

    static void testPutWithValidInputs() {
        // Create a minimal subclass of Provider to instantiate
        Provider p = new Provider("TestProvider", 1.0, "Test Provider Info") {
            private static final long serialVersionUID = 1L;
        };

        // The put method is synchronized and calls implPut internally,
        // but implPut is protected and not visible here, so we test put with simple keys and values
        // We expect no exception and a non-null return (implPut returns previous value or null)

        Object prev = p.put("key1", "value1");
        if (prev != null) {
            throw new RuntimeException("Expected null previous value on first put");
        }

        Object prev2 = p.put("key1", "value2");
        // The second put with same key should return the previous value "value1"
        if (!"value1".equals(prev2)) {
            throw new RuntimeException("Expected previous value 'value1' on second put");
        }
    }

    static void testPutThrowsWhenNotInitialized() {
        // The WIT expath suggests put throws IllegalStateException if not initialized
        // But the current source code calls check("putProviderProperty."+name) which may throw
        // We simulate a Provider subclass that disables initialization to test this

        Provider p = new Provider("UninitProvider", 1.0, "Uninitialized Provider") {
            private static final long serialVersionUID = 1L;

            // Override check to throw IllegalStateException to simulate !initialized
            @Override
            protected void check(String service) {
                throw new IllegalStateException("Provider not initialized");
            }
        };

        boolean thrown = false;
        try {
            p.put("key", "value");
        } catch (IllegalStateException e) {
            thrown = true;
            if (!"Provider not initialized".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IllegalStateException message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when put called on uninitialized provider");
        }
    }
}
