/*
 * @test
 * @summary Test ConcurrentHashMap.forEach with valid and null BiConsumer
 * @run main ConcurrentHashMapWitupTest_Pa582d3547e65
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.Node;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest_Pa582d3547e65 {
    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
    }

    private static void testForEachWithValidConsumer() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        final StringBuilder sb = new StringBuilder();
        BiConsumer<String, Integer> action = new BiConsumer<String, Integer>() {
            public void accept(String k, Integer v) {
                sb.append(k).append("=").append(v).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        // Check that all keys and values were processed
        if (!result.contains("one=1") || !result.contains("two=2") || !result.contains("three=3")) {
            throw new AssertionError("forEach did not process all entries: " + result);
        }
    }

    private static void testForEachWithNullConsumer() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
