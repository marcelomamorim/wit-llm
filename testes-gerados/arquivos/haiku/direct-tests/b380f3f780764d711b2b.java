/*
 * @test
 * @summary Behavioral tests for TreeMap.replaceAll(BiFunction)
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplacesValuesUsingKeyAndValue() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.replaceAll((k, v) -> k.length() + v * 10);
        check(map.get("a") == 11, "a expected 11 but was " + map.get("a"));
        check(map.get("b") == 21, "b expected 21 but was " + map.get("b"));
        check(map.get("c") == 31, "c expected 31 but was " + map.get("c"));
        check(map.size() == 3, "size should remain 3");
    }

    static void testKeysAndOrderPreserved() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("z", 1);
        map.put("m", 2);
        map.put("a", 3);
        map.replaceAll((k, v) -> v * 2);
        check(map.firstKey().equals("a"), "first key should be a");
        check(map.lastKey().equals("z"), "last key should be z");
        check(map.get("m") == 4, "m expected 4");
    }

    static void testEmptyMapNoCalls() {
        TreeMap<String, Integer> map = new TreeMap<>();
        final int[] calls = {0};
        map.replaceAll((k, v) -> {
            calls[0]++;
            return v;
        });
        check(calls[0] == 0, "function should not be called on empty map");
        check(map.isEmpty(), "map should remain empty");
    }

    static void testNullFunctionThrowsNpe() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        try {
            map.replaceAll(null);
            throw new RuntimeException("NullPointerException expected");
        } catch (NullPointerException expected) {
            check(map.get("a") == 1, "value must not change when function is null");
        }
    }

    static void testStructuralModificationThrowsCme() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        try {
            map.replaceAll((k, v) -> {
                if (k.equals("a")) {
                    map.put("c", 99);
                }
                return v + 100;
            });
            throw new RuntimeException("ConcurrentModificationException expected");
        } catch (ConcurrentModificationException expected) {
            check(map.get("a") == 101, "current entry value should have been replaced before CME");
        }
    }

    public static void main(String[] args) {
        testReplacesValuesUsingKeyAndValue();
        testKeysAndOrderPreserved();
        testEmptyMapNoCalls();
        testNullFunctionThrowsNpe();
        testStructuralModificationThrowsCme();
    }
}
