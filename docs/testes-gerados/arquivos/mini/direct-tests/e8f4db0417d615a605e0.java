/*
 * @test
 * @summary Test Provider.configure(String) throws UnsupportedOperationException
 * @run main ProviderWitupTest
 */

public class ProviderWitupTest {
    public static void main(String[] args) {
        java.security.Provider p = new java.security.Provider("TestProvider", 1.0, "Test Info") {};
        try {
            p.configure("anyArg");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
