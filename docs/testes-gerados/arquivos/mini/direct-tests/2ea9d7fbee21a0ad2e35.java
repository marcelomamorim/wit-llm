/*
 * @test
 * @summary Test ConcurrentHashMap.forEach(Consumer) method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.Node;
import java.util.concurrent.ConcurrentHashMap.Traverser;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachOnEmptyMap();
        testForEachOnPopulatedMap();
    }

    static void testForEachWithNullConsumer() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.forEach(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachOnEmptyMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        final int[] count = {0};
        map.forEach(new Consumer<String>() {
            public void accept(String s) {
                count[0]++;
            }
        });
        if (count[0] != 0) {
            throw new Error("forEach on empty map should not invoke consumer");
        }
    }

    static void testForEachOnPopulatedMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");

        final StringBuilder sb = new StringBuilder();
        map.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });

        String result = sb.toString();
        // The keys "one", "two", "three" must all be present in the output
        if (!(result.contains("one") && result.contains("two") && result.contains("three"))) {
            throw new Error("forEach did not visit all keys: " + result);
        }
    }
}
