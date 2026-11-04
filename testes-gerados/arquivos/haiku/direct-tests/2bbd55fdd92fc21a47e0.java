/*
 * @test
 * @summary Verify Provider.getServices() returns an empty, cached, unmodifiable view for a new provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Set;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "witup test provider");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        Set<Provider.Service> services = p.getServices();
        check(services != null, "getServices() must not return null");
        check(services.isEmpty(), "new provider without services must return empty set");

        Set<Provider.Service> again = p.getServices();
        check(services == again, "unchanged provider must return the cached set instance");

        boolean threw = false;
        try {
            services.add(null);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        check(threw, "returned set must be unmodifiable");
    }
}
