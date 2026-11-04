/*
 * @test
 * @summary Test ConcurrentSkipListMap.forEachRemaining with null and non-null Consumer
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingWithEntries();
    }

    static void testForEachRemainingNullConsumer() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithEntries() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        final StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = new Consumer<Integer>() {
            public void accept(Integer i) {
                sb.append(i).append(",");
            }
        };
        map.forEachRemaining(consumer);
        // The forEachRemaining method is from KeySpliterator, which iterates keys in ascending order
        String result = sb.toString();
        if (!result.equals("1,2,")) {
            throw new RuntimeException("Unexpected keys iterated: " + result);
        }
    }
}
