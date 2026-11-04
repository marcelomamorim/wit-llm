/*
 * @test
 * @summary Test java.security.Provider.getServices() behavior including initialization check
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.security.Provider.Service;
import java.util.Set;

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

        // We must override checkInitialized to throw if not initialized
        @Override
        protected void checkInitialized() {
            if (!initialized) {
                throw new IllegalStateException("Provider not initialized");
            }
        }

        // Expose getServices for testing
        @Override
        public synchronized Set<Service> getServices() {
            return super.getServices();
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider("TestProvider", 1.0, "Test Provider");

        // Test that getServices throws IllegalStateException if not initialized
        try {
            p.getServices();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // Expected
        }

        // Now set initialized and test getServices returns a non-null Set
        p.setInitialized(true);

        Set<Service> services = p.getServices();
        if (services == null) {
            throw new RuntimeException("getServices() returned null after initialization");
        }

        // The returned set should be unmodifiable (attempt to add should throw)
        try {
            services.add(null);
            throw new RuntimeException("Returned services set is modifiable");
        } catch (UnsupportedOperationException uoe) {
            // Expected
        }

        System.out.println("TestProviderWitupTest passed");
    }
}
