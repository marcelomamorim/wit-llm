/*
 * @test
 * @summary Witup-derived behavioral tests for ConcurrentHashMap(Map) constructor
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testCopiesEntries();
        testEmptySource();
        testNullMapThrowsNPE();
        testNullKeyInSourceThrowsNPE();
        testResultIsIndependentCopy();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopiesEntries() {
        Map<String, Integer> source = new HashMap<>();
        source.put("a", 1);
        source.put("b", 2);
        source.put("c", 3);
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(source);
        check(map.size() == 3, "size should be 3, was " + map.size());
        check(map.get("a") == 1, "a should map to 1");
        check(map.get("b") == 2, "b should map to 2");
        check(map.get("c") == 3, "c should map to 3");
        check(map.get("d") == null, "absent key should return null");
    }

    static void testEmptySource() {
        Map<String, Integer> source = new HashMap<>();
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(source);
        check(map.isEmpty(), "map copied from empty source should be empty");
        check(map.size() == 0, "size of empty copy should be 0");
        map.put("x", 10);
        check(map.get("x") == 10, "empty-copied map should remain usable");
    }

    static void testNullMapThrowsNPE() {
        boolean thrown = false;
        try {
            new ConcurrentHashMap<String, Integer>((Map<String, Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null map argument should throw NullPointerException");
    }

    static void testNullKeyInSourceThrowsNPE() {
        Map<String, Integer> source = new HashMap<>();
        source.put(null, 1);
        boolean thrown = false;
        try {
            new ConcurrentHashMap<String, Integer>(source);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "source containing null key should throw NullPointerException");
    }

    static void testResultIsIndependentCopy() {
        Map<String, Integer> source = new HashMap<>();
        source.put("k", 1);
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(source);
        source.put("k2", 2);
        source.put("k", 99);
        check(map.size() == 1, "later changes to source must not change copy size");
        check(map.get("k") == 1, "later changes to source must not change copied value");
        check(map.get("k2") == null, "later additions to source must not appear in copy");
    }
}
