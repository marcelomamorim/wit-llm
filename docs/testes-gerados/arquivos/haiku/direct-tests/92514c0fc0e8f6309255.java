/*
 * @test
 * @summary Verifies observable behavior of ConcurrentSkipListMap.getOrDefault for absent, present, removed and comparator-based keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testAbsentKeyReturnsDefault();
        testPresentKeyReturnsValue();
        testAbsentKeyWithNullDefaultReturnsNull();
        testRemovedKeyReturnsDefault();
        testComparatorBasedLookup();
    }

    static void testAbsentKeyReturnsDefault() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        check(m.getOrDefault("missing", 7) == 7, "absent key should return default value");
    }

    static void testPresentKeyReturnsValue() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        check(m.getOrDefault("a", 7) == 1, "present key should return its mapped value, not default");
    }

    static void testAbsentKeyWithNullDefaultReturnsNull() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        check(m.getOrDefault("b", null) == null, "absent key with null default should return null");
    }

    static void testRemovedKeyReturnsDefault() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        m.remove("a");
        check(m.getOrDefault("a", 9) == 9, "removed key should return default value");
    }

    static void testComparatorBasedLookup() {
        ConcurrentSkipListMap<String, Integer> ci = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
        ci.put("Key", 5);
        check(ci.getOrDefault("KEY", 0) == 5, "lookup should honor the comparator");
        check(ci.getOrDefault("other", 0) == 0, "comparator lookup of absent key should return default");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
