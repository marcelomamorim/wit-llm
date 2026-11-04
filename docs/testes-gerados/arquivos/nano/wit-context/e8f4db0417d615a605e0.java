/* @test    @summary Test for Provider.configure method to verify it throws UnsupportedOperationException.    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testConfigureThrowsException();
    }

    public static void testConfigureThrowsException() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public Provider configure(String configArg) {
                throw new UnsupportedOperationException("configure is not supported");
            }
        };
        try {
            provider.configure("someConfig");
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
            System.out.println("Caught expected UnsupportedOperationException.");
        }
    }
}
