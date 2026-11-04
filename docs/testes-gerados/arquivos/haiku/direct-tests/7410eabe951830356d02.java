/*
 * @test
 * @summary Verifies observable behavior of Provider.remove(Object key) for stored and missing properties
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

    static void testRemoveReturnsPreviousValue() {
        TestProvider p = new TestProvider();
        p.put("Test.Key", "value");
        Object removed = p.remove("Test.Key");
        check("value".equals(removed), "remove should return previous value, got " + removed);
        check(p.get("Test.Key") == null, "key should be absent after remove");
    }

    static void testRemoveMissingKeyReturnsNull() {
        TestProvider p = new TestProvider();
        Object removed = p.remove("Missing.Key");
        check(removed == null, "remove of missing key should return null, got " + removed);
    }

    static void testRemoveTwiceReturnsNullSecondTime() {
        TestProvider p = new TestProvider();
        p.put("Test.Twice", "v");
        check("v".equals(p.remove("Test.Twice")), "first remove should return value");
        check(p.remove("Test.Twice") == null, "second remove should return null");
    }

    static void testRemoveOnlyTargetKey() {
        TestProvider p = new TestProvider();
        p.put("Test.A", "1");
        p.put("Test.B", "2");
        p.remove("Test.A");
        check(p.get("Test.A") == null, "Test.A should be removed");
        check("2".equals(p.get("Test.B")), "Test.B should remain");
    }

    public static void main(String[] args) {
        testRemoveReturnsPreviousValue();
        testRemoveMissingKeyReturnsNull();
        testRemoveTwiceReturnsNullSecondTime();
        testRemoveOnlyTargetKey();
    }
}
