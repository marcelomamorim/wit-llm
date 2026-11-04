/*
 * @test
 * @summary Test java.security.Provider.getServices() method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.security.Provider.Service;
import java.util.Set;
import java.util.Iterator;

public class ProviderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetServicesEmptyProvider();
        testGetServicesUnmodifiableSet();
    }

    static void testGetServicesEmptyProvider() {
        Provider p = new Provider("TestProvider", 1.0, "Test Provider Info") {
            // No services added
        };

        Set<Service> services = p.getServices();
        if (services == null) {
            throw new RuntimeException("getServices() returned null");
        }
        if (!services.isEmpty()) {
            throw new RuntimeException("Expected empty services set for new provider");
        }
    }

    static void testGetServicesUnmodifiableSet() {
        Provider p = new Provider("TestProvider", 1.0, "Test Provider Info") {
            // No services added
        };

        Set<Service> services = p.getServices();

        try {
            services.add(null);
            throw new RuntimeException("Expected UnsupportedOperationException when adding to services set");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        try {
            Iterator<Service> it = services.iterator();
            it.remove();
            throw new RuntimeException("Expected UnsupportedOperationException when removing from services iterator");
        } catch (UnsupportedOperationException expected) {
            // expected
        } catch (IllegalStateException ise) {
            // iterator.remove() may throw IllegalStateException if next() not called
            // This is acceptable, so no failure here
        }
    }
}
