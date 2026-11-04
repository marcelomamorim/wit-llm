/*
 * @test
 * @summary Provider.configure(String) must always throw UnsupportedOperationException with a fixed message
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("TestProvider", "1.0", "test info");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();
        check(p, "abc");
        check(p, "");
        check(p, null);
    }

    static void check(Provider p, String arg) {
        try {
            p.configure(arg);
            throw new RuntimeException("No exception thrown for arg: " + arg);
        } catch (UnsupportedOperationException e) {
            if (!"configure is not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
