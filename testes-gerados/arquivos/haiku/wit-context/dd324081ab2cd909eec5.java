/*
 * @test
 * @summary Witup-derived tests for ConcurrentSkipListMap.replace(K,V)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsAndLeavesMapUnchanged();
        testNullValueThrowsAndLeavesMapUnchanged();
        testAbsentKeyReturnsNullAndDoesNotInsert();
        testPresentKeyReplacesAndReturnsOldValue();
    }

    static void testNullKeyThrowsAndLeavesMapUnchanged() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        boolean thrown = false;
        try {
            map.replace(null, "x");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "replace(null, value) should throw NullPointerException");
        check(map.size() == 1, "map size changed after failed replace with null key");
        check("1".equals(map.get("a")), "existing value changed after failed replace with null key");
    }

    static void testNullValueThrowsAndLeavesMapUnchanged() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        boolean thrown = false;
        try {
            map.replace("a", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "replace(key, null) should throw NullPointerException");
        check(map.size() == 1, "map size changed after failed replace with null value");
        check("1".equals(map.get("a")), "existing value changed after failed replace with null value");
    }

    static void testAbsentKeyReturnsNullAndDoesNotInsert() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        String old = map.replace("b", "2");
        check(old == null, "replace on absent key should return null");
        check(!map.containsKey("b"), "replace on absent key must not insert the key");
        check(map.size() == 1, "map size changed after replace on absent key");
    }

    static void testPresentKeyReplacesAndReturnsOldValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        String old = map.replace("a", "9");
        check("1".equals(old), "replace should return previous value");
        check("9".equals(map.get("a")), "replace should store new value");
        check(map.size() == 1, "replace on present key must not change size");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
