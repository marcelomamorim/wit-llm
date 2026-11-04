/*
 * @test
 * @summary WITUP-derived behavioral tests for WeakHashMap.replaceAll
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.function.BiFunction;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testReplaceAllTransformsValues();
        testNullKeyIsPassedAsNull();
        testNullValueIsPassedToFunction();
        testEmptyMapDoesNotInvokeFunction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFunctionThrowsNPE() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("a", "1");
        boolean thrown = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "replaceAll(null) must throw NullPointerException");
        check("1".equals(map.get("a")), "map must be unchanged after NPE");
    }

    static void testReplaceAllTransformsValues() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.replaceAll((k, v) -> k + v);
        check("a1".equals(map.get("a")), "value for a should be a1");
        check("b2".equals(map.get("b")), "value for b should be b2");
        check(map.size() == 2, "size should remain 2");
    }

    static void testNullKeyIsPassedAsNull() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put(null, "x");
        map.replaceAll((k, v) -> k == null ? "null-key" : v + "!");
        check("null-key".equals(map.get(null)), "null key should be passed as null to function");
    }

    static void testNullValueIsPassedToFunction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("a", null);
        map.replaceAll((k, v) -> v == null ? "filled" : v);
        check("filled".equals(map.get("a")), "null value should be passed to function");
    }

    static void testEmptyMapDoesNotInvokeFunction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        int[] calls = {0};
        map.replaceAll((k, v) -> {
            calls[0]++;
            return v;
        });
        check(calls[0] == 0, "function must not be invoked on empty map");
        check(map.isEmpty(), "empty map must stay empty");
    }
}
