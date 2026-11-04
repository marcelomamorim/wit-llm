/*
 * @test
 * @summary Test java.util.HashMap.replaceAll method behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllEmptyMap();
        testReplaceAllBasicFunctionality();
        testReplaceAllConcurrentModification();
    }

    static void testReplaceAllNullFunction() {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.replaceAll(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceAllEmptyMap() {
        HashMap<String, String> map = new HashMap<>();
        // Should not throw and map remains empty
        map.replaceAll((k, v) -> "newValue");
        if (!map.isEmpty()) {
            throw new Error("Map should remain empty after replaceAll on empty map");
        }
    }

    static void testReplaceAllBasicFunctionality() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        map.replaceAll(new BiFunction<String, Integer, Integer>() {
            public Integer apply(String k, Integer v) {
                return v * 10;
            }
        });

        if (map.get("a") != 10 || map.get("b") != 20 || map.get("c") != 30) {
            throw new Error("replaceAll did not correctly update values");
        }
    }

    static void testReplaceAllConcurrentModification() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");

        try {
            map.replaceAll(new BiFunction<String, String, String>() {
                public String apply(String k, String v) {
                    // Concurrent modification during replaceAll
                    map.put("anotherKey", "anotherValue");
                    return v;
                }
            });
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
