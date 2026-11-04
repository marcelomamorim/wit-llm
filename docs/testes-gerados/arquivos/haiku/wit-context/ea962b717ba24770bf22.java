/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap submap replace(key, value)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNPE();
        testInRangeReplaceReturnsOldValue();
        testAbsentInRangeKeyIsNotInserted();
        testOutOfRangeKeyThrowsIAE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullKeyThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, true, 3, true);
        try {
            sub.replace(null, "v");
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(map.size() == 1, "map size changed after failed replace");
        check("a".equals(map.get(1)), "value changed after failed replace");
    }

    static void testInRangeReplaceReturnsOldValue() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, true, 3, false);
        String old = sub.replace(1, "x");
        check("a".equals(old), "expected old value a but was " + old);
        check("x".equals(map.get(1)), "replace did not update backing map");
        check("b".equals(map.get(2)), "unrelated mapping changed");
    }

    static void testAbsentInRangeKeyIsNotInserted() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.remove(2);
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, true, 3, false);
        String old = sub.replace(2, "z");
        check(old == null, "expected null for absent key but was " + old);
        check(!map.containsKey(2), "replace must not insert absent key");
        check(map.size() == 1, "unexpected size after replace of absent key");
    }

    static void testOutOfRangeKeyThrowsIAE() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, true, 3, false);
        try {
            sub.replace(3, "x");
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check("c".equals(map.get(3)), "out-of-range mapping was modified");
    }
}
