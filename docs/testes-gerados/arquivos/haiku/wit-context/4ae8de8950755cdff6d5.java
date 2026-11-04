/*
 * @test
 * @summary Witup-derived tests for ConcurrentHashMap.computeIfAbsent
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNPE();
        testNullFunctionThrowsNPE();
        testExistingValueNotRecomputed();
        testAbsentKeyComputed();
        testNullResultNotStored();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullKeyThrowsNPE() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent(null, k -> "v");
            throw new RuntimeException("expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
        check(map.isEmpty(), "map must remain empty after null key failure");
    }

    static void testNullFunctionThrowsNPE() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent("a", null);
            throw new RuntimeException("expected NullPointerException for null function");
        } catch (NullPointerException expected) {
        }
        check(map.isEmpty(), "map must remain empty after null function failure");
    }

    static void testExistingValueNotRecomputed() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "x");
        final boolean[] called = new boolean[1];
        Function<String, String> fn = k -> {
            called[0] = true;
            return "y";
        };
        String result = map.computeIfAbsent("a", fn);
        check("x".equals(result), "expected existing value x but got " + result);
        check(!called[0], "mapping function must not be called when key is present");
        check("x".equals(map.get("a")), "existing mapping must be preserved");
    }

    static void testAbsentKeyComputed() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("b", k -> "v2");
        check("v2".equals(result), "expected computed value v2 but got " + result);
        check("v2".equals(map.get("b")), "computed value must be stored");
        check(map.size() == 1, "expected size 1 but got " + map.size());
    }

    static void testNullResultNotStored() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.computeIfAbsent("c", k -> null);
        check(result == null, "expected null result but got " + result);
        check(!map.containsKey("c"), "null mapping result must not be stored");
        check(map.isEmpty(), "map must remain empty");
    }
}
