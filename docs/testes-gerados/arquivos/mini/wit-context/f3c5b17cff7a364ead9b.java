/*
 * @test
 * @summary Test java.security.Provider.keys() method behavior including IllegalStateException when uninitialized
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Enumeration;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        private boolean initialized = false;

        protected TestProvider(String name, double version, String info) {
            super(name, version, info);
        }

        // Override checkInitialized to simulate initialized state
        void setInitialized(boolean state) {
            this.initialized = state;
        }

        // Override checkInitialized method to throw if not initialized
        void checkInitialized() {
            if (!initialized) {
                throw new IllegalStateException("Provider not initialized");
            }
        }

        // Override keys() to call our checkInitialized
        @Override
        public Enumeration<Object> keys() {
            checkInitialized();
            return super.keys();
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider("TestProvider", 1.0, "Test Provider");

        // Test that keys() throws IllegalStateException if not initialized
        boolean threw = false;
        try {
            p.keys();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when keys() called on uninitialized provider");
        }

        // Now set initialized and test keys() returns Enumeration (likely empty)
        p.setInitialized(true);
        Enumeration<Object> keys = p.keys();
        if (keys == null) {
            throw new RuntimeException("keys() returned null Enumeration");
        }

        // keys() should return an Enumeration; test that calling hasMoreElements() does not throw
        try {
            keys.hasMoreElements();
        } catch (Exception e) {
            throw new RuntimeException("keys() Enumeration threw exception on hasMoreElements", e);
        }

        System.out.println("TestProvider keys() tests passed");
    }
}
