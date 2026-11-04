/*
 * @test
 * @summary White-box-free WIT-derived checks for IdentityHashMap.replaceAll(BiFunction)
 * @run main IdentityHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.IdentityHashMap;
import java.util.Map;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testAppliesFunctionByReferenceIdentity();
        testNullKeyIsPassedAsNull();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFunctionThrowsNPE() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        map.put("a", 1);
        try {
            map.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected per Objects.requireNonNull(function)
        }
        check(map.get("a") != null && map.get("a") == 1,
              "Map must be unchanged after rejected replaceAll");
    }

    static void testAppliesFunctionByReferenceIdentity() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        String k1 = new String("x");
        String k2 = new String("x");
        map.put(k1, 1);
        map.put(k2, 2);
        map.replaceAll((k, v) -> v * 10);
        check(map.size() == 2, "Equal-content but distinct keys must remain separate");
        check(map.get(k1).intValue() == 10, "Value for k1 not replaced");
        check(map.get(k2).intValue() == 20, "Value for k2 not replaced");
    }

    static void testNullKeyIsPassedAsNull() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        map.put(null, 5);
        map.replaceAll((k, v) -> {
            if (k != null) {
                throw new IllegalStateException("Null key must be passed as null");
            }
            return v + 1;
        });
        check(map.get(null).intValue() == 6, "Null key value not replaced");
    }

    static void testConcurrentModificationDetected() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        map.put(new String("a"), 1);
        try {
            map.replaceAll((k, v) -> {
                map.put(new String("b"), 2);
                return v;
            });
            throw new RuntimeException("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected: structural modification detected via modCount check
        }
    }
}
