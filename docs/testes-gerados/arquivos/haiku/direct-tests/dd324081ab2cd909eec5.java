/*
 * @test
 * @summary Tests ConcurrentSkipListMap.replace(K, V) observable behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplacePresentKeyReturnsOldValueAndUpdates();
        testReplaceAbsentKeyReturnsNullAndDoesNotInsert();
        testReplaceOnEmptyMapReturnsNull();
        testNullKeyThrowsNpe();
        testNullValueThrowsNpe();
        testNullKeyOnEmptyMapThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplacePresentKeyReturnsOldValueAndUpdates() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        String old = map.replace(1, "x");
        check("a".equals(old), "expected old value a but was " + old);
        check("x".equals(map.get(1)), "expected updated value x");
        check("b".equals(map.get(2)), "other mapping must be unchanged");
        check(map.size() == 2, "size must remain 2");
    }

    static void testReplaceAbsentKeyReturnsNullAndDoesNotInsert() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        String result = map.replace(3, "y");
        check(result == null, "expected null for absent key");
        check(!map.containsKey(3), "replace must not insert absent key");
        check(map.size() == 2, "size must remain 2");
    }

    static void testReplaceOnEmptyMapReturnsNull() {
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        check(empty.replace(5, "z") == null, "expected null on empty map");
        check(empty.isEmpty(), "map must remain empty");
    }

    static void testNullKeyThrowsNpe() {
        final ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        boolean thrown = false;
        try {
            map.replace(null, "x");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null key");
        check("a".equals(map.get(1)), "map must be unchanged after NPE");
    }

    static void testNullValueThrowsNpe() {
        final ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        boolean thrown = false;
        try {
            map.replace(1, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null value");
        check("a".equals(map.get(1)), "map must be unchanged after NPE");
    }

    static void testNullKeyOnEmptyMapThrowsNpe() {
        final ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        boolean thrown = false;
        try {
            empty.replace(null, "x");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null key on empty map");
        check(empty.isEmpty(), "map must remain empty");
    }
}
