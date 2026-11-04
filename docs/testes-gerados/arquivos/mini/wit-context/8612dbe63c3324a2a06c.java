/*
 * @test
 * @summary Test java.security.Provider.remove(Object key, Object value) method
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsWhenNotInitialized();
        testRemoveDelegatesToImplRemove();
    }

    private static void testRemoveThrowsWhenNotInitialized() {
        // Create a Provider subclass to control initialization state
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private boolean initialized = false;

            @Override
            protected synchronized boolean implRemove(Object key, Object value) {
                return true; // dummy
            }

            // Override check to simulate !initialized throwing IllegalStateException
            @Override
            protected void check(String action) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }

            // Method to set initialized flag
            void setInitialized(boolean val) {
                initialized = val;
            }
        };

        try {
            p.remove("key", "value");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    private static void testRemoveDelegatesToImplRemove() {
        // Create a Provider subclass with initialized true and implRemove tracking
        Provider p = new Provider("TestProvider", 1.0, "Test Provider") {
            private boolean initialized = true;
            private boolean implRemoveCalled = false;
            private Object lastKey = null;
            private Object lastValue = null;

            @Override
            protected synchronized boolean implRemove(Object key, Object value) {
                implRemoveCalled = true;
                lastKey = key;
                lastValue = value;
                return true;
            }

            @Override
            protected void check(String action) {
                if (!initialized) {
                    throw new IllegalStateException("Provider not initialized");
                }
            }

            boolean wasImplRemoveCalled() {
                return implRemoveCalled;
            }

            Object getLastKey() {
                return lastKey;
            }

            Object getLastValue() {
                return lastValue;
            }
        };

        boolean result = p.remove("myKey", "myValue");
        if (!result) {
            throw new RuntimeException("Expected remove to return true");
        }
        if (!p.wasImplRemoveCalled()) {
            throw new RuntimeException("implRemove was not called by remove");
        }
        if (!"myKey".equals(p.getLastKey()) || !"myValue".equals(p.getLastValue())) {
            throw new RuntimeException("implRemove called with wrong arguments");
        }
    }
}
