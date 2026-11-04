/*
 * @test
 * @summary Witup-derived behavioral tests for TreeMap.get(Object)
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testGetExistingKeyReturnsValue();
        testGetMissingKeyReturnsNull();
        testGetNullKeyWithNaturalOrderingThrowsNPE();
        testGetNullKeyWithNullAwareComparatorReturnsValue();
        testGetOnEmptyMapReturnsNull();
    }

    static void testGetExistingKeyReturnsValue() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        check(map.get("a") != null && map.get("a") == 1, "existing key a");
        check(map.get("b") != null && map.get("b") == 2, "existing key b");
    }

    static void testGetMissingKeyReturnsNull() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        check(map.get("z") == null, "missing key must return null");
    }

    static void testGetNullKeyWithNaturalOrderingThrowsNPE() {
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

    static void testGetNullKeyWithNullAwareComparatorReturnsValue() {
        Comparator<String> cmp = new Comparator<String>() {
            @Override
            public int compare(String x, String y) {
                if (x == null) {
                    return (y == null) ? 0 : -1;
                }
                if (y == null) {
                    return 1;
                }
                return x.compareTo(y);
            }
        };
        TreeMap<String, Integer> map = new TreeMap<>(cmp);
        map.put(null, 42);
        map.put("a", 1);
        Integer v = map.get(null);
        check(v != null && v == 42, "get(null) with null-aware comparator must return mapped value");
    }

    static void testGetOnEmptyMapReturnsNull() {
        TreeMap<String, Integer> map = new TreeMap<>();
        check(map.get("x") == null, "get on empty map must return null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Assertion failed: " + message);
        }
    }
}
