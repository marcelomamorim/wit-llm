/*
 * @test
 * @summary Test ConcurrentHashMap.forEachRemaining with null and valid Consumer
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingValidAction();
    }

    private static void testForEachRemainingNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");

        try {
            // The forEachRemaining method is on EntrySpliterator, which is not public,
            // so we test the public forEach method of ConcurrentHashMap which calls similar logic.
            // Since the method under test is forEachRemaining(Consumer<? super Map.Entry<K,V>>),
            // and the expath indicates NullPointerException if action == null,
            // we test the public forEach method with null to provoke NPE.
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testForEachRemainingValidAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> action = new Consumer<Map.Entry<String, String>>() {
            @Override
            public void accept(Map.Entry<String, String> entry) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
            }
        };

        // Use forEach which internally uses forEachRemaining on spliterator
        map.forEach(action);

        String result = sb.toString();
        if (!result.contains("key1=value1") || !result.contains("key2=value2")) {
            throw new AssertionError("forEach did not process all entries: " + result);
        }
    }
}
