/*
 * @test
 * @summary Observable basic behavior of a Provider subclass created via the protected constructor
 * @run main ProviderWitupTest
 */
public class ProviderWitupTest {
    public static void main(String[] args) {
        Provider p = new Provider("WitupProvider", "1.0", "witup info") { };
        check(p.getName(), "WitupProvider");
        check(p.getVersionStr(), "1.0");
        check(p.getInfo(), "witup info");
    }

    private static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
