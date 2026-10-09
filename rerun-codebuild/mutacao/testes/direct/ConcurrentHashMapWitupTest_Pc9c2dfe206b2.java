/*
 * @test
 * @summary Test replaceAll method of ConcurrentHashMap for correctness and exception handling
 * @run main ConcurrentHashMapWitupTest_Pc9c2dfe206b2
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_Pc9c2dfe206b2 {

    public static void main(String[] args) {
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullFunction();
        testReplaceAllWithNullValues();
        testReplaceAllWithFunctionReturningNull();
    }

    static void testReplaceAllWithValidFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.replaceAll((k, v) -> v * 10);
        assert map.get("a") == 10 : "Expected 10, got " + map.get("a");
        assert map.get("b") == 20 : "Expected 20, got " + map.get("b");
    }

    static void testReplaceAllWithNullFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        try {
            map.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceAllWithNullValues() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        BiFunction<String, Integer, Integer> func = (k, v) -> null;
        try {
            map.replaceAll(func);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceAllWithFunctionReturningNull() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        BiFunction<String, Integer, Integer> func = (k, v) -> null;
        try {
            map.replaceAll(func);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
