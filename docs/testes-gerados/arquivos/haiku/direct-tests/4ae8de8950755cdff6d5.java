/*
 * @test
 * @summary Behavioral tests for ConcurrentHashMap.computeIfAbsent: null arguments, absent and present keys, null-returning mapping functions, and exception propagation
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNPE();
        testNullFunctionThrowsNPE();
        testAbsentKeyMapsValue();
        testPresentKeyReturnsExistingWithoutCallingFunction();
        testNullResultDoesNotMap();
        testNullResultThenLaterValue();
        testFunctionExceptionOnEmptyMapPropagatesAndLeavesNoMapping();
        testFunctionExceptionWithCollidingEntryPropagatesAndLeavesNoMapping();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    static void testNullKeyThrowsNPE() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent(null, k -> "x");
            throw new AssertionError("expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
        check(map.isEmpty(), "map must remain empty after null key");
    }

    static void testNullFunctionThrowsNPE() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent(1, null);
            throw new AssertionError("expected NullPointerException for null function");
        } catch (NullPointerException e) {
            // expected
        }
        check(map.isEmpty(), "map must remain empty after null function");
    }

    static void testAbsentKeyMapsValue() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        String v = map.computeIfAbsent(1, k -> "v" + k);
        check("v1".equals(v), "returned value should be v1 but was " + v);
        check("v1".equals(map.get(1)), "get(1) should be v1");
        check(map.size() == 1, "size should be 1");
    }

    static void testPresentKeyReturnsExistingWithoutCallingFunction() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(5, "existing");
        int[] calls = {0};
        Function<Integer, String> f = k -> {
            calls[0]++;
            return "new";
        };
        String v = map.computeIfAbsent(5, f);
        check("existing".equals(v), "should return existing value");
        check(calls[0] == 0, "function must not be called for present key");
        check("existing".equals(map.get(5)), "mapping must be unchanged");
    }

    static void testNullResultDoesNotMap() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        String v = map.computeIfAbsent(7, k -> null);
        check(v == null, "returned value should be null");
        check(!map.containsKey(7), "key must not be mapped after null result");
        check(map.size() == 0, "size should remain 0");
    }

    static void testNullResultThenLaterValue() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.computeIfAbsent(7, k -> null);
        String v = map.computeIfAbsent(7, k -> "later");
        check("later".equals(v), "second call should map the key");
        check("later".equals(map.get(7)), "get(7) should be later");
    }

    static void testFunctionExceptionOnEmptyMapPropagatesAndLeavesNoMapping() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfAbsent(3, k -> {
                throw new IllegalArgumentException("boom");
            });
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check("boom".equals(e.getMessage()), "unexpected message");
        }
        check(!map.containsKey(3), "key must not be mapped after exception");
        check(map.size() == 0, "size should be 0 after exception");
        String v = map.computeIfAbsent(3, k -> "ok");
        check("ok".equals(v), "map must remain usable after exception");
    }

    static void testFunctionExceptionWithCollidingEntryPropagatesAndLeavesNoMapping() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(16);
        map.put(1, "one");
        try {
            map.computeIfAbsent(17, k -> {
                throw new IllegalArgumentException("boom2");
            });
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check("boom2".equals(e.getMessage()), "unexpected message");
        }
        check(!map.containsKey(17), "key 17 must not be mapped after exception");
        check("one".equals(map.get(1)), "existing entry must be preserved");
        check(map.size() == 1, "size should remain 1");
    }
}
