/*
 * @test
 * @summary Checks ConcurrentSkipListMap.replace(K, V, V) for matching, non-matching, absent-key and null-argument cases
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplaceMatchingValue();
        testReplaceNonMatchingValue();
        testReplaceAbsentKey();
        testNullKeyThrowsNpe();
        testNullOldValueThrowsNpe();
        testNullNewValueThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceMatchingValue() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        check(map.replace(1, "a", "b"), "replace with matching old value should return true");
        check("b".equals(map.get(1)), "value should be replaced by newValue");
    }

    static void testReplaceNonMatchingValue() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        check(!map.replace(1, "x", "b"), "replace with non-matching old value should return false");
        check("a".equals(map.get(1)), "value should remain unchanged when old value does not match");
    }

    static void testReplaceAbsentKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        check(!map.replace(2, "a", "b"), "replace on absent key should return false");
        check(!map.containsKey(2), "absent key should not be inserted by replace");
        check(map.isEmpty(), "map should remain empty");
    }

    static void testNullKeyThrowsNpe() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        boolean thrown = false;
        try {
            map.replace(null, "a", "b");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null key should throw NullPointerException");
    }

    static void testNullOldValueThrowsNpe() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        boolean thrown = false;
        try {
            map.replace(1, null, "b");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null oldValue should throw NullPointerException");
        check("a".equals(map.get(1)), "value should be unchanged after NPE");
    }

    static void testNullNewValueThrowsNpe() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        boolean thrown = false;
        try {
            map.replace(1, "a", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null newValue should throw NullPointerException");
        check("a".equals(map.get(1)), "value should be unchanged after NPE");
    }
}
