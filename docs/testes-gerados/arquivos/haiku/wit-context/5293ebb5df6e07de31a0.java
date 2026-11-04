/*
 * @test
 * @summary Witup-generated checks for ConcurrentHashMap.putIfAbsent null handling and absent/present key semantics
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNpe();
        testNullValueThrowsNpe();
        testAbsentKeyInsertsAndReturnsNull();
        testPresentKeyKeepsExistingValue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullKeyThrowsNpe() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.putIfAbsent(null, "value");
        } catch (NullPointerException e) {
            check(map.isEmpty(), "map must remain empty after null key");
            return;
        }
        throw new RuntimeException("expected NullPointerException for null key");
    }

    static void testNullValueThrowsNpe() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.putIfAbsent("key", null);
        } catch (NullPointerException e) {
            check(map.isEmpty(), "map must remain empty after null value");
            check(!map.containsKey("key"), "null value must not be stored");
            return;
        }
        throw new RuntimeException("expected NullPointerException for null value");
    }

    static void testAbsentKeyInsertsAndReturnsNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String previous = map.putIfAbsent("k", "v1");
        check(previous == null, "putIfAbsent on absent key must return null");
        check("v1".equals(map.get("k")), "value must be inserted for absent key");
        check(map.size() == 1, "size must be 1 after insertion");
    }

    static void testPresentKeyKeepsExistingValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("k", "v1");
        String previous = map.putIfAbsent("k", "v2");
        check("v1".equals(previous), "putIfAbsent on present key must return existing value");
        check("v1".equals(map.get("k")), "existing value must not be replaced");
        check(map.size() == 1, "size must remain 1");
    }
}
