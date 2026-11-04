/*
 * @test
 * @summary Tests Provider.getAttribute null handling and missing attribute behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "test info");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullNameThrowsNPE() {
        TestProvider p = new TestProvider();
        boolean thrown = false;
        try {
            p.getAttribute(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "getAttribute(null) must throw NullPointerException");
    }

    static void testMissingAttributeReturnsNull() {
        TestProvider p = new TestProvider();
        check(p.getAttribute("NoSuchAttribute") == null,
              "getAttribute of unknown name must return null");
    }

    static void testRepeatedLookupIsStable() {
        TestProvider p = new TestProvider();
        String first = p.getAttribute("NoSuchAttribute");
        String second = p.getAttribute("NoSuchAttribute");
        check(first == null && second == null,
              "repeated lookup of missing attribute must consistently return null");
    }

    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testMissingAttributeReturnsNull();
        testRepeatedLookupIsStable();
    }
}
