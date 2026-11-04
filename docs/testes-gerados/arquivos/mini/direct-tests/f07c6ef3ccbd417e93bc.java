/*
 * @test
 * @summary Test ConcurrentHashMap.forEachRemaining with valid and null Consumer
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.Node;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithEntries();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithEntries() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();

        // Create an iterator over the map's entrySet to get a forEachRemaining method
        // The method under test is from an internal iterator class, but we test the public API
        // Since the method is from ConcurrentHashMap, we simulate a simple iterator with forEachRemaining

        // We use the map.entrySet().iterator() which has forEachRemaining(Consumer<? super Map.Entry<K,V>>)
        // to indirectly test the behavior similar to the method source

        map.entrySet().iterator().forEachRemaining(new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append(",");
            }
        });

        String result = sb.toString();
        if (!(result.contains("key1=value1") && result.contains("key2=value2"))) {
            throw new AssertionError("forEachRemaining did not process all entries: " + result);
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            // The method under test throws NullPointerException if action is null
            // We simulate this by calling forEachRemaining with null on the iterator
            map.entrySet().iterator().forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
