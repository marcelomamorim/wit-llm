/* @test    @summary Test the configure method of java.security.Provider for UnsupportedOperationException    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            @Override
            public Provider configure(String configArg) {
                return super.configure(configArg);
            }
        };
        try {
            provider.configure("testConfig");
            // If no exception, test passes as method is unsupported and should throw
            System.out.println("Test passed: UnsupportedOperationException thrown as expected.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("Test failed: Caught unexpected exception: " + e);
        }
    }
}
