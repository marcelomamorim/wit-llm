/*
 * @test
 * @summary Behavioral tests for HashMap.replaceAll(BiFunction)
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;

public class HashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFunctionThrowsNPE() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null function");
        check(map.get("a") == 1, "map must be unchanged after NPE");
    }

    static void testReplacesValues() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.replaceAll((k, v) -> v * 10);
        check(map.get("a") == 10, "a should be 10 but was " + map.get("a"));
        check(map.get("b") == 20, "b should be 20 but was " + map.get("b"));
        check(map.size() == 2, "size must remain 2");
    }

    static void testKeysAreUnchanged() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x", 5);
        map.put("y", 6);
        map.replaceAll((k, v) -> v + 1);
        check(map.containsKey("x") && map.containsKey("y"), "keys must be preserved");
        check(map.size() == 2, "size must remain 2");
    }

    static void testFunctionReceivesNullValue() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", null);
        map.replaceAll((k, v) -> v == null ? 0 : v + 1);
        check(map.get("a") == 0, "null value should be passed to function and replaced");
    }

    static void testEmptyMapDoesNotInvokeFunction() {
        HashMap<String, Integer> map = new HashMap<>();
        final int[] calls = new int[1];
        map.replaceAll((k, v) -> {
            calls[0]++;
            return v;
        });
        check(calls[0] == 0, "function must not be called for empty map");
        check(map.isEmpty(), "map must remain empty");
    }

    static void testConcurrentModificationDetected() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        final boolean[] inserted = new boolean[1];
        boolean thrown = false;
        try {
            map.replaceAll((k, v) -> {
                if (!inserted[0]) {
                    inserted[0] = true;
                    map.put("newKey", 99);
                }
                return v + 1;
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException when structure changes");
    }

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testReplacesValues();
        testKeysAreUnchanged();
        testFunctionReceivesNullValue();
        testEmptyMapDoesNotInvokeFunction();
        testConcurrentModificationDetected();
    }
}
