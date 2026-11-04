/*
 * @test
 * @summary Test ConcurrentHashMap.replaceAll with null function and normal usage
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllBasicFunction();
    }

    private static void testReplaceAllNullFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testReplaceAllBasicFunction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        BiFunction<String, String, String> toUpper = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                return v.toUpperCase();
            }
        };

        map.replaceAll(toUpper);

        if (!"VALUE1".equals(map.get("key1"))) {
            throw new RuntimeException("replaceAll did not update value for key1 correctly");
        }
        if (!"VALUE2".equals(map.get("key2"))) {
            throw new RuntimeException("replaceAll did not update value for key2 correctly");
        }
    }
}
