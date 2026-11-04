/*
 * @test
 * @summary Test ConcurrentHashMap.forEach with null and non-null Consumer
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.Node;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullConsumer();
        testForEachWithEntries();
    }

    static void testForEachNullConsumer() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testForEachWithEntries() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> action = new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        if (!(result.contains("key1=value1") && result.contains("key2=value2"))) {
            throw new RuntimeException("forEach did not process all entries correctly: " + result);
        }
    }
}
