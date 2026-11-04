/*
 * @test
 * @summary Verify TreeMap.replace(K, V) returns the previous value for present keys, returns null and leaves the map unchanged for absent keys, and honors a custom comparator.
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKeyReturnsOldValue();
        testReplaceExistingKeyUpdatesMapping();
        testReplaceExistingKeyDoesNotChangeSize();
        testReplaceAbsentKeyReturnsNull();
        testReplaceAbsentKeyDoesNotInsert();
        testReplaceOnEmptyMapReturnsNull();
        testReplaceWithNullValueOnExistingKey();
        testReplaceWithComparatorUsesComparatorEquality();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceExistingKeyReturnsOldValue() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        Integer old = map.replace("a", 2);
        check(old != null && old.intValue() == 1, "expected previous value 1, got " + old);
    }

    static void testReplaceExistingKeyUpdatesMapping() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.replace("a", 2);
        check(map.get("a") != null && map.get("a").intValue() == 2, "expected new value 2");
    }

    static void testReplaceExistingKeyDoesNotChangeSize() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.replace("a", 3);
        check(map.size() == 2, "expected size 2, got " + map.size());
    }

    static void testReplaceAbsentKeyReturnsNull() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        check(map.replace("b", 2) == null, "expected null for absent key");
    }

    static void testReplaceAbsentKeyDoesNotInsert() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.replace("b", 2);
        check(!map.containsKey("b"), "replace must not insert absent key");
        check(map.size() == 1, "expected size 1, got " + map.size());
    }

    static void testReplaceOnEmptyMapReturnsNull() {
        TreeMap<String, Integer> map = new TreeMap<>();
        check(map.replace("x", 1) == null, "expected null on empty map");
        check(map.isEmpty(), "empty map must remain empty");
    }

    static void testReplaceWithNullValueOnExistingKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        Integer old = map.replace("a", null);
        check(old != null && old.intValue() == 1, "expected previous value 1");
        check(map.containsKey("a"), "key must remain present");
        check(map.get("a") == null, "expected null value after replace");
    }

    static void testReplaceWithComparatorUsesComparatorEquality() {
        Comparator<String> cmp = String.CASE_INSENSITIVE_ORDER;
        TreeMap<String, Integer> map = new TreeMap<>(cmp);
        map.put("Key", 10);
        Integer old = map.replace("KEY", 20);
        check(old != null && old.intValue() == 10, "expected previous value 10 via comparator match");
        check(map.size() == 1, "expected size 1, got " + map.size());
        check(map.get("key") != null && map.get("key").intValue() == 20, "expected value 20 under comparator equality");
    }
}
