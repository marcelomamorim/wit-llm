/*
 * @test
 * @summary Witup-generated behavioral checks for Provider.getServices()
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Set;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider(String name) {
            super(name, "1.0", "test info");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider("WitupProv");

        Set<Provider.Service> services = p.getServices();
        if (services == null) {
            throw new RuntimeException("getServices() returned null");
        }
        if (!services.isEmpty()) {
            throw new RuntimeException("fresh provider should expose no services");
        }

        Set<Provider.Service> again = p.getServices();
        if (!services.equals(again)) {
            throw new RuntimeException("repeated getServices() calls differ");
        }

        try {
            services.add(null);
            throw new RuntimeException("getServices() result must be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // expected: Collections.unmodifiableSet view
        }

        if (!"WitupProv".equals(p.getName())) {
            throw new RuntimeException("unexpected provider name");
        }
    }
}
