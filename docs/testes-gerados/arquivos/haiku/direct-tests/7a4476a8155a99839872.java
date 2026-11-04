/*
 * @test
 * @summary Behavioral tests for Hashtable.computeIfAbsent covering insertion, existing keys, null results, null arguments and concurrent modification
 * @run main HashtableWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testAbsentKeyInsertsComputedValue();
        testExistingKeyReturnsValueWithoutCallingFunction();
        testNullResultIsNotInserted();
        testNullMappingFunctionThrowsNPE();
        testNullKeyThrowsNPE();
        testModificationDuringFunctionThrowsCME();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAbsentKeyInsertsComputedValue() {
        Hashtable<String, Integer> h = new Hashtable<>();
        Integer v = h.computeIfAbsent("a", k -> k.length() + 1);
        check(v == 2, "expected returned value 2 but was " + v);
        check(h.get("a") == 2, "expected stored value 2");
        check(h.size() == 1, "expected size 1 but was " + h.size());
    }

    static void testExistingKeyReturnsValueWithoutCallingFunction() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 10);
        int[] calls = {0};
        Integer v = h.computeIfAbsent("a", k -> {
            calls[0]++;
            return 99;
        });
        check(v == 10, "expected existing value 10 but was " + v);
        check(calls[0] == 0, "mapping function must not be called for present key");
        check(h.get("a") == 10, "existing mapping must be preserved");
        check(h.size() == 1, "size must remain 1");
    }

    static void testNullResultIsNotInserted() {
        Hashtable<String, Integer> h = new Hashtable<>();
        Integer v = h.computeIfAbsent("a", k -> null);
        check(v == null, "expected null result");
        check(!h.containsKey("a"), "null value must not be inserted");
        check(h.isEmpty(), "table must remain empty");
    }

    static void testNullMappingFunctionThrowsNPE() {
        Hashtable<String, Integer> h = new Hashtable<>();
        boolean thrown = false;
        try {
            h.computeIfAbsent("a", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null mapping function");
        check(h.isEmpty(), "table must remain empty");
    }

    static void testNullKeyThrowsNPE() {
        Hashtable<String, Integer> h = new Hashtable<>();
        boolean thrown = false;
        try {
            h.computeIfAbsent(null, k -> 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null key");
        check(h.isEmpty(), "table must remain empty");
    }

    static void testModificationDuringFunctionThrowsCME() {
        Hashtable<String, Integer> h = new Hashtable<>();
        boolean thrown = false;
        try {
            h.computeIfAbsent("a", k -> {
                h.put("b", 2);
                return 1;
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException");
        check(!h.containsKey("a"), "key must not be inserted after CME");
        check(h.get("b") == 2, "concurrent put must remain visible");
    }
}
