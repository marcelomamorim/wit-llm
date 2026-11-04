/*
 * @test
 * @summary Tests ConcurrentHashMap.replace(K, V, V) null checks, matching and mismatching old values, and absent keys
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullArgumentsThrow();
        testReplaceWithMatchingOldValue();
        testReplaceWithMismatchedOldValue();
        testReplaceAbsentKeyReturnsFalseWithoutInsert();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentsThrow() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");

        boolean thrown = false;
        try {
            map.replace(null, "1", "2");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null key must throw NullPointerException");

        thrown = false;
        try {
            map.replace("a", null, "2");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null oldValue must throw NullPointerException");

        thrown = false;
        try {
            map.replace("a", "1", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null newValue must throw NullPointerException");

        check("1".equals(map.get("a")), "map must be unchanged after null-argument calls");
        check(map.size() == 1, "size must be unchanged after null-argument calls");
    }

    static void testReplaceWithMatchingOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("k", "old");

        check(map.replace("k", "old", "new"), "replace with matching old value must return true");
        check("new".equals(map.get("k")), "value must be updated to newValue");
        check(map.size() == 1, "size must remain 1");
    }

    static void testReplaceWithMismatchedOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("k", "old");

        check(!map.replace("k", "other", "new"), "replace with mismatched old value must return false");
        check("old".equals(map.get("k")), "value must remain unchanged on mismatch");
    }

    static void testReplaceAbsentKeyReturnsFalseWithoutInsert() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        check(!map.replace("missing", "old", "new"), "replace on absent key must return false");
        check(!map.containsKey("missing"), "replace must not insert absent key");
        check(map.isEmpty(), "map must remain empty");
    }
}
