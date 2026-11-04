/*
 * @test
 * @summary Test ConcurrentSkipListMap.forEachRemaining with null and normal Consumer
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithNull();
        testForEachRemainingWithConsumer();
    }

    static void testForEachRemainingWithNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        try {
            // The forEachRemaining method is on the ValueSpliterator, so get values().spliterator()
            map.values().spliterator().forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithConsumer() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        map.values().spliterator().forEachRemaining(consumer);

        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two")) {
            throw new RuntimeException("forEachRemaining did not process all values: " + result);
        }
    }
}
