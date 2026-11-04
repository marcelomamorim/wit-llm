/*
 * @test
 * @summary Test java.security.Provider.clear() method behavior including IllegalStateException when uninitialized
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        testClearThrowsWhenUninitialized();
        testClearNormalBehavior();
    }

    private static void testClearThrowsWhenUninitialized() {
        // Create a subclass to access protected constructor
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            // Override check to simulate uninitialized state throwing IllegalStateException
            @Override
            protected void check(String service) {
                throw new IllegalStateException("Provider not initialized");
            }

            @Override
            protected void implClear() {
                // no-op
            }
        };

        try {
            p.clear();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
            if (!"Provider not initialized".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testClearNormalBehavior() {
        // Create a subclass to test normal clear behavior
        final boolean[] implClearCalled = {false};
        Provider p = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            protected void check(String service) {
                // no exception, simulate initialized
            }

            @Override
            protected void implClear() {
                implClearCalled[0] = true;
            }
        };

        p.clear();
        if (!implClearCalled[0]) {
            throw new RuntimeException("implClear() was not called by clear()");
        }
    }
}
