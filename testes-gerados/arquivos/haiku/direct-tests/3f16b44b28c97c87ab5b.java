/*
 * @test
 * @summary Behavioral tests for TreeMap.get(Object key)
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testGetOnEmptyMapReturnsNull() {
        TreeMap<String, Integer> map = new TreeMap<>();
        check(map.get("a") == null, "get on empty map must return null");
    }

    static void testGetExistingKeyReturnsValue() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        check(map.get("a") != null && map.get("a") == 1, "get(a) must return 1");
        check(map.get("b") != null && map.get("b") == 2, "get(b) must return 2");
    }

    static void testGetMissingKeyReturnsNull() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("c", 3);
        check(map.get("b") == null, "get of absent key must return null");
    }

    static void testGetAfterRemoveReturnsNull() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("x", 10);
        map.remove("x");
        check(map.get("x") == null, "get after remove must return null");
    }

    static void testGetUsesComparator() {
        TreeMap<String, Integer> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put("abc", 7);
        check(map.get("ABC") != null && map.get("ABC") == 7,
              "get must use the comparator for key equivalence");
    }

    static void testGetWithCustomComparatorMissingKey() {
        Comparator<String> reverse = Comparator.reverseOrder();
        TreeMap<String, Integer> map = new TreeMap<>(reverse);
        map.put("a", 1);
        map.put("b", 2);
        check(map.get("c") == null, "get of absent key with comparator must return null");
        check(map.get("b") != null && map.get("b") == 2, "get(b) with comparator must return 2");
    }

    static void testGetNullValueReturnsNull() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("k", null);
        check(map.get("k") == null, "get of key mapped to null must return null");
    }

    static void testGetNullKeyNaturalOrderingThrowsNPE() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.get(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "get(null) with natural ordering must throw NullPointerException");
    }

    public static void main(String[] args) {
        testGetOnEmptyMapReturnsNull();
        testGetExistingKeyReturnsValue();
        testGetMissingKeyReturnsNull();
        testGetAfterRemoveReturnsNull();
        testGetUsesComparator();
        testGetWithCustomComparatorMissingKey();
        testGetNullValueReturnsNull();
        testGetNullKeyNaturalOrderingThrowsNPE();
    }
}
