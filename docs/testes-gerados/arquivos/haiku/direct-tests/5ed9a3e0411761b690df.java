/*
 * @test
 * @summary Verifies ConcurrentSkipListMap.remove(Object, Object) observable behavior: conditional removal, null value handling, comparator-based key matching, and NullPointerException for null key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testConditionalRemoval();
        testNullValueReturnsFalse();
        testMissingKeyReturnsFalse();
        testNullKeyThrowsNPE();
        testComparatorBasedKeyMatching();
    }

    static void testConditionalRemoval() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        check(!m.remove("a", 2), "remove with different value must return false");
        check(m.containsKey("a"), "mapping must remain after non-matching remove");
        check(m.get("a") == 1, "value must be unchanged after non-matching remove");
        check(m.remove("a", 1), "remove with matching key and value must return true");
        check(!m.containsKey("a"), "mapping must be gone after successful remove");
        check(!m.remove("a", 1), "second remove of same mapping must return false");
    }

    static void testNullValueReturnsFalse() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        check(!m.remove("a", null), "null value must return false");
        check(m.containsKey("a"), "mapping must remain when value is null");
    }

    static void testMissingKeyReturnsFalse() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        check(!m.remove("missing", 1), "absent key must return false");
        check(m.isEmpty(), "map must stay empty");
    }

    static void testNullKeyThrowsNPE() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        boolean thrown = false;
        try {
            m.remove(null, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null key must throw NullPointerException");
        check(m.containsKey("a"), "map must be unchanged after NPE");
    }

    static void testComparatorBasedKeyMatching() {
        ConcurrentSkipListMap<String, Integer> m =
                new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
        m.put("Key", 1);
        check(m.remove("KEY", 1), "key equal under comparator with equal value must be removed");
        check(m.isEmpty(), "map must be empty after comparator-based removal");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
