/* @test    @summary Test for Provider.getAttribute(String) method handling null input    @run main ProviderWitupTest */

import java.security.Provider;

public class ProviderWitupTest {

    public static void main(String[] args) {
        testGetAttributeWithNull();
    }

    public static void testGetAttributeWithNull() {
        Provider provider = new Provider("TestProvider", 1.0, "Test Info") {
            // No additional implementation needed for this test
        };
        try {
            provider.getAttribute(null);
            System.out.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
