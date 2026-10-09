/* @test    @summary Test replaceAll method of ConcurrentHashMap for thread-safe update behavior    @run main ConcurrentHashMapWitupTest_P6ddbf69fa628 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_P6ddbf69fa628 {

    public static void main(String[] args) {
        testReplaceAllWithNonNullFunction();
        testReplaceAllWithNullFunctionThrows();
        testReplaceAllWithNullValues();
        testReplaceAllEmptyMap();
    }

    static void testReplaceAllWithNonNullFunction() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        BiFunction<Integer, String, String> func = (k, v) -> v.toUpperCase();
        map.replaceAll(func);
        assert "ONE".equals(map.get(1)) : "Expected 'ONE' for key 1";
        assert "TWO".equals(map.get(2)) : "Expected 'TWO' for key 2";
        assert "THREE".equals(map.get(3)) : "Expected 'THREE' for key 3";
    }

    static void testReplaceAllWithNullFunctionThrows() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        boolean threw = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        assert threw : "Expected NullPointerException when passing null function";
    }

    static void testReplaceAllWithNullValues() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        BiFunction<Integer, String, String> func = (k, v) -> null;
        boolean threw = false;
        try {
            map.replaceAll(func);
        } catch (NullPointerException e) {
            threw = true;
        }
        assert threw : "Expected NullPointerException when function returns null";
        assert "one".equals(map.get(1)) : "Value should remain unchanged due to exception";
    }

    static void testReplaceAllEmptyMap() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        BiFunction<Integer, String, String> func = (k, v) -> v + "!";
        map.replaceAll(func); // Should not throw or change anything
        assert map.isEmpty() : "Map should still be empty";
    }
}
