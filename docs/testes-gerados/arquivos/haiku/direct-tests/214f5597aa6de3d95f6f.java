/*
 * @test
 * @summary Tests ConcurrentHashMap.remove(Object key, Object value)
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNullPointerException();
        testNullValueReturnsFalseAndKeepsEntry();
        testAbsentKeyReturnsFalse();
        testNonMatchingValueReturnsFalseAndKeepsEntry();
        testMatchingValueRemovesEntry();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullKeyThrowsNullPointerException() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        boolean thrown = false;
        try {
            map.remove(null, "1");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "remove(null, value) should throw NullPointerException");
        check("1".equals(map.get("a")), "map should be unchanged after null key");
    }

    static void testNullValueReturnsFalseAndKeepsEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        check(!map.remove("a", null), "remove(key, null) should return false");
        check(map.containsKey("a"), "entry should remain after remove(key, null)");
        check("1".equals(map.get("a")), "value should remain unchanged");
    }

    static void testAbsentKeyReturnsFalse() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        check(!map.remove("missing", "1"), "remove on absent key should return false");
        check(map.isEmpty(), "map should remain empty");
    }

    static void testNonMatchingValueReturnsFalseAndKeepsEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        check(!map.remove("a", "2"), "remove with non-matching value should return false");
        check(map.containsKey("a"), "entry should remain when value does not match");
        check("1".equals(map.get("a")), "original value should be preserved");
    }

    static void testMatchingValueRemovesEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        check(map.remove("a", "1"), "remove with matching value should return true");
        check(!map.containsKey("a"), "entry should be removed");
        check("2".equals(map.get("b")), "other entries should be unaffected");
        check(map.size() == 1, "size should be 1 after removal");
    }
}
