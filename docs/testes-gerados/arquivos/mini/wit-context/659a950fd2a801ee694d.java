/*
 * @test
 * @summary Test java.security.Provider.Service constructor behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Collections;

public class ProviderWitupTest {
    public static void main(String[] args) {
        // Create a dummy Provider subclass instance to pass to Service constructor
        Provider dummyProvider = new Provider("TestProvider", 1.0, "Test Info") {
            private static final long serialVersionUID = 1L;
        };

        // Invoke the private Service constructor via a subclass to test observable behavior
        // Since Service is private, we test via reflection only if absolutely necessary
        // Here, we test the constructor behavior indirectly by subclassing Provider and
        // creating an inner subclass of Service for test purposes.

        // Because Service is private, we cannot instantiate it directly.
        // Instead, we test the constructor logic by creating a subclass inside this test.

        class TestService extends Provider.Service {
            TestService(Provider p) {
                super(p);
            }

            // Expose aliases and attributes for test
            java.util.List<String> getAliases() {
                return aliases;
            }

            java.util.Map<UString, String> getAttributes() {
                return attributes;
            }

            Provider getProvider() {
                return provider;
            }
        }

        TestService service = new TestService(dummyProvider);

        // Check that provider field is set correctly
        if (service.getProvider() != dummyProvider) {
            throw new AssertionError("Provider field not set correctly");
        }

        // Check that aliases is empty list
        if (!service.getAliases().equals(Collections.emptyList())) {
            throw new AssertionError("Aliases is not empty list");
        }

        // Check that attributes is empty map
        if (!service.getAttributes().equals(Collections.emptyMap())) {
            throw new AssertionError("Attributes is not empty map");
        }

        System.out.println("Test passed.");
    }
}
