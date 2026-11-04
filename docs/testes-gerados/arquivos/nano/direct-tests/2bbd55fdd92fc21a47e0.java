/*
 * @test
 * @summary Test getServices() method of java.security.Provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.security.Provider.Service;
import java.util.Set;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetServicesReturnsUnmodifiableSet();
        testGetServicesReturnsSameSetOnMultipleCalls();
    }

    private static void testGetServicesReturnsUnmodifiableSet() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public synchronized Set<Service> getServices() {
                return super.getServices();
            }
        };
        Set<Service> services = provider.getServices();
        try {
            services.add(null);
            throw new AssertionError("Expected UnsupportedOperationException when modifying set");
        } catch (UnsupportedOperationException expected) {
            // pass
        }
    }

    private static void testGetServicesReturnsSameSetOnMultipleCalls() {
        Provider provider = new Provider("TestProvider2", 1.0, "Test Info") {
            @Override
            public synchronized Set<Service> getServices() {
                return super.getServices();
            }
        };
        Set<Service> firstCall = provider.getServices();
        Set<Service> secondCall = provider.getServices();
        if (firstCall != secondCall) {
            throw new AssertionError("getServices() should return the same set instance on multiple calls");
        }
    }
}
