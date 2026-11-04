/*
 * @test
 * @summary Behavioral tests for ConcurrentHashMap.replaceAll(BiFunction)
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testUpdatesAllValues();
        testEmptyMapDoesNotInvokeFunction();
        testNullReturnValueThrowsNPE();
        testFunctionReceivesKeyAndOldValue();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFunctionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        try {
            map.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testUpdatesAllValues() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.replaceAll((k, v) -> v * 10);
        check(map.size() == 3, "size must be unchanged");
        check(map.get("a") == 10, "a should be 10 but was " + map.get("a"));
        check(map.get("b") == 20, "b should be 20 but was " + map.get("b"));
        check(map.get("c") == 30, "c should be 30 but was " + map.get("c"));
    }

    static void testEmptyMapDoesNotInvokeFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        int[] calls = {0};
        map.replaceAll((k, v) -> {
            calls[0]++;
            return v;
        });
        check(calls[0] == 0, "function must not be invoked on empty map");
        check(map.isEmpty(), "map must remain empty");
    }

    static void testNullReturnValueThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        try {
            map.replaceAll((k, v) -> null);
            throw new RuntimeException("Expected NullPointerException when function returns null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFunctionReceivesKeyAndOldValue() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("x", 5);
        map.put("y", 7);
        map.replaceAll((k, v) -> k.equals("x") ? v + 1 : v);
        check(map.get("x") == 6, "x should be 6 but was " + map.get("x"));
        check(map.get("y") == 7, "y should be 7 but was " + map.get("y"));
    }
}
