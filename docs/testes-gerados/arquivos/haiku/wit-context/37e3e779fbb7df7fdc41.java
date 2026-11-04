/*
 * @test
 * @summary Behavioral tests for Provider.getProperty(String) on the current checkout
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "witup test provider");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMissingKeyReturnsNull() {
        TestProvider p = new TestProvider();
        check(p.getProperty("absent.key") == null, "missing key must return null");
    }

    static void testStoredPropertyIsReturned() {
        TestProvider p = new TestProvider();
        p.put("Alg.Impl", "value1");
        check("value1".equals(p.getProperty("Alg.Impl")), "stored property must be returned");
    }

    static void testOverwrittenPropertyReturnsLatestValue() {
        TestProvider p = new TestProvider();
        p.put("k", "first");
        p.put("k", "second");
        check("second".equals(p.getProperty("k")), "latest value must be returned");
    }

    static void testNullKeyThrowsNullPointerException() {
        TestProvider p = new TestProvider();
        try {
            p.getProperty(null);
            throw new RuntimeException("null key must throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testMissingKeyReturnsNull();
        testStoredPropertyIsReturned();
        testOverwrittenPropertyReturnsLatestValue();
        testNullKeyThrowsNullPointerException();
    }
}
