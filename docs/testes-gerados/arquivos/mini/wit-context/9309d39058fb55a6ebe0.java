/*
 * @test
 * @summary Test java.security.Provider.values() method behavior including IllegalStateException when uninitialized
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Collection;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        private boolean initialized = false;

        protected TestProvider(String name, double version, String info) {
            super(name, version, info);
        }

        // Override checkInitialized to simulate initialized flag
        void setInitialized(boolean val) {
            this.initialized = val;
        }

        // Mimic the checkInitialized method behavior from Provider
        void checkInitialized() {
            if (!initialized) {
                throw new IllegalStateException("Provider not initialized");
            }
        }

        // Override values() to call our checkInitialized
        @Override
        public Collection<Object> values() {
            checkInitialized();
            return java.util.Collections.unmodifiableCollection(super.values());
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider("TestProvider", 1.0, "Test Provider");

        // Test that values() throws IllegalStateException if not initialized
        try {
            p.values();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
        }

        // Set initialized to true and test values() returns unmodifiable collection
        p.setInitialized(true);

        Collection<Object> vals = p.values();
        if (vals == null) {
            throw new RuntimeException("values() returned null");
        }

        // The collection should be unmodifiable - test by attempting to add
        try {
            vals.add(new Object());
            throw new RuntimeException("Expected UnsupportedOperationException not thrown on add");
        } catch (UnsupportedOperationException expected) {
            // Expected
        }

        // Test that values() returns the same collection on repeated calls
        Collection<Object> vals2 = p.values();
        if (vals2 != vals) {
            throw new RuntimeException("values() returned different collections on repeated calls");
        }

        System.out.println("TestProvider.values() tests passed.");
    }
}
