/*
 * @test
 * @summary Test java.security.Provider.remove(Object) method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveThrowsWhenNotInitialized();
        testRemoveReturnsNullForUnknownKey();
    }

    private static void testRemoveThrowsWhenNotInitialized() {
        // Create a minimal subclass to instantiate Provider
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;
        };

        // We expect IllegalStateException because check("removeProviderProperty."+name) calls checkInitialized internally
        // and the Provider is not initialized by default.
        try {
            p.remove("someKey");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testRemoveReturnsNullForUnknownKey() {
        // Create a minimal subclass to instantiate Provider
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private static final long serialVersionUID = 1L;

            // Override check to do nothing to simulate initialized state
            @Override
            protected void check(String permission) {
                // do nothing, simulate initialized
            }

            // Override implRemove to simulate removal behavior
            @Override
            protected Object implRemove(Object key) {
                // Return null to simulate key not found
                return null;
            }
        };

        Object result = p.remove("nonExistentKey");
        if (result != null) {
            throw new RuntimeException("Expected null return for unknown key");
        }
    }
}
