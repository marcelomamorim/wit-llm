/*
 * @test
 * @summary Verifies the observable behavior of Provider.remove(Object key, Object value)
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("TestProvider", "1.0", "test info");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testRemoveMatchingKeyAndValue();
        testRemoveWithDifferentValueKeepsEntry();
        testRemoveMissingKeyReturnsFalse();
    }

    static void testRemoveMatchingKeyAndValue() {
        TestProvider p = new TestProvider();
        p.put("key1", "value1");
        boolean removed = p.remove("key1", "value1");
        check(removed, "remove with matching key and value should return true");
        check(p.get("key1") == null, "entry should be absent after successful remove");
    }

    static void testRemoveWithDifferentValueKeepsEntry() {
        TestProvider p = new TestProvider();
        p.put("key2", "value2");
        boolean removed = p.remove("key2", "otherValue");
        check(!removed, "remove with non-matching value should return false");
        check("value2".equals(p.get("key2")), "entry should remain when value does not match");
    }

    static void testRemoveMissingKeyReturnsFalse() {
        TestProvider p = new TestProvider();
        boolean removed = p.remove("missing", "anything");
        check(!removed, "remove of absent key should return false");
    }
}
