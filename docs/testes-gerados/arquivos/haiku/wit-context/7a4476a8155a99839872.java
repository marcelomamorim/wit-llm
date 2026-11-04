/*
 * @test
 * @summary Witup-derived behavioral tests for Hashtable.computeIfAbsent
 * @run main HashtableWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNullMappingFunctionThrowsNPE();
        testAbsentKeyStoresComputedValue();
        testPresentKeyReturnsExistingValueWithoutInvokingFunction();
        testNullComputedValueIsNotStored();
        testNullKeyThrowsNPE();
        testConcurrentModificationIsDetected();
    }

    static void testNullMappingFunctionThrowsNPE() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        try {
            ht.computeIfAbsent("a", null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null mappingFunction");
    }

    static void testAbsentKeyStoresComputedValue() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        Integer v = ht.computeIfAbsent("a", k -> 42);
        if (v == null || v.intValue() != 42) {
            throw new RuntimeException("Unexpected return value: " + v);
        }
        if (ht.get("a") == null || ht.get("a").intValue() != 42) {
            throw new RuntimeException("Computed value not stored");
        }
    }

    static void testPresentKeyReturnsExistingValueWithoutInvokingFunction() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("a", 1);
        int[] calls = new int[1];
        Integer v = ht.computeIfAbsent("a", k -> { calls[0]++; return 99; });
        if (v == null || v.intValue() != 1) {
            throw new RuntimeException("Expected existing value 1, got " + v);
        }
        if (calls[0] != 0) {
            throw new RuntimeException("Mapping function must not be invoked for present key");
        }
        if (ht.get("a").intValue() != 1) {
            throw new RuntimeException("Existing mapping was altered");
        }
    }

    static void testNullComputedValueIsNotStored() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        Integer v = ht.computeIfAbsent("a", k -> null);
        if (v != null) {
            throw new RuntimeException("Expected null result, got " + v);
        }
        if (ht.containsKey("a")) {
            throw new RuntimeException("Null computed value must not create a mapping");
        }
    }

    static void testNullKeyThrowsNPE() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        try {
            ht.computeIfAbsent(null, k -> 1);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null key");
    }

    static void testConcurrentModificationIsDetected() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        try {
            ht.computeIfAbsent("a", k -> {
                ht.put("b", 2);
                return 1;
            });
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new RuntimeException("Expected ConcurrentModificationException");
    }
}
