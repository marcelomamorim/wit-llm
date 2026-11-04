/* @test    @summary Test the private constructor of Provider.Service class for correct initialization.    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testServiceConstructor();
    }

    public static void testServiceConstructor() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {};
        Provider.Service service = new Provider.Service(provider);
        assert service != null : "Service instance should not be null";
        assert service.provider == provider : "Provider should be correctly assigned";
        assert service.aliases.isEmpty() : "Aliases should be empty";
        assert service.attributes.isEmpty() : "Attributes should be empty";
    }
}
