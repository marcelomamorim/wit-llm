/*
 * @test
 * @summary Test ConcurrentHashMap.replaceAll with various BiFunction inputs
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllEmptyMap();
        testReplaceAllSimpleUpdate();
        testReplaceAllFunctionReturnsNull();
    }

    static void testReplaceAllNullFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceAllEmptyMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.replaceAll(new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                throw new RuntimeException("Should not be called on empty map");
            }
        });
        if (!map.isEmpty()) {
            throw new RuntimeException("Map should remain empty after replaceAll on empty map");
        }
    }

    static void testReplaceAllSimpleUpdate() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        map.replaceAll(new BiFunction<String, Integer, Integer>() {
            public Integer apply(String k, Integer v) {
                return v + 10;
            }
        });

        if (map.get("a") != 11 || map.get("b") != 12 || map.get("c") != 13) {
            throw new RuntimeException("replaceAll did not update values correctly");
        }
    }

    static void testReplaceAllFunctionReturnsNull() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        try {
            map.replaceAll(new BiFunction<String, String, String>() {
                public String apply(String k, String v) {
                    return null;
                }
            });
            throw new RuntimeException("Expected NullPointerException when function returns null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
