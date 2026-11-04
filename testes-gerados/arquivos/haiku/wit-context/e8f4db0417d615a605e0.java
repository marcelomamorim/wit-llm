/*
 * @test
 * @summary Verify Provider.configure always throws UnsupportedOperationException
 * @run main ProviderWitupTest
 */
public class ProviderWitupTest {
    private static class TestProvider extends Provider {
        TestProvider() {
            super("TestProvider", "1.0", "test info");
        }
    }

    public static void main(String[] args) {
        Provider p = new TestProvider();
        checkThrows(p, "");
        checkThrows(p, null);
        checkThrows(p, "some-arg");
    }

    private static void checkThrows(Provider p, String arg) {
        try {
            p.configure(arg);
            throw new RuntimeException("expected UnsupportedOperationException for arg: " + arg);
        } catch (UnsupportedOperationException e) {
            if (!"configure is not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
