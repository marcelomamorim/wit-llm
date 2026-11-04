/*
 * @test
 * @summary Test ConcurrentHashMap.forEachRemaining with null and valid Consumer
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.Node;
import java.util.concurrent.ConcurrentHashMap.ValueSpliterator;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingWithValues();
    }

    static void testForEachRemainingNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        ValueSpliterator<String, String> spliterator = map.new ValueSpliterator<>(0, 0, 0, map.table, 0, 0);
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithValues() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        ValueSpliterator<String, String> spliterator = map.new ValueSpliterator<>(0, 0, 0, map.table, 0, 0);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String v) {
                if (sb.length() > 0) sb.append(",");
                sb.append(v);
            }
        };

        spliterator.forEachRemaining(consumer);

        String result = sb.toString();
        if (!(result.contains("value1") && result.contains("value2"))) {
            throw new AssertionError("forEachRemaining did not process all values: " + result);
        }
    }
}
