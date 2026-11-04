/*
 * @test
 * @summary Tests ConcurrentHashMap.replaceAll null-function guard and null-result handling
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testNullFunctionOnEmptyMapThrowsNPE();
        testNullResultThrowsNPE();
        testEmptyMapNoOp();
        testReplaceAllUpdatesValues();
    }

    static void testNullFunctionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        boolean thrown = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null function");
        }
    }

    static void testNullFunctionOnEmptyMapThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        boolean thrown = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null function on empty map");
        }
    }

    static void testNullResultThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        BiFunction<String, Integer, Integer> f = (k, v) -> null;
        boolean thrown = false;
        try {
            map.replaceAll(f);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException when function returns null");
        }
    }

    static void testEmptyMapNoOp() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        BiFunction<String, Integer, Integer> f = (k, v) -> {
            throw new IllegalStateException("should not be called");
        };
        map.replaceAll(f);
        if (!map.isEmpty()) {
            throw new RuntimeException("empty map should remain empty");
        }
    }

    static void testReplaceAllUpdatesValues() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.replaceAll((k, v) -> v * 10);
        if (map.get("a") != 10 || map.get("b") != 20 || map.size() != 2) {
            throw new RuntimeException("unexpected values after replaceAll: " + map);
        }
    }
}
