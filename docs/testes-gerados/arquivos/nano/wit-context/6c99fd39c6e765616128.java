/*
 * @test
 * @summary Test for ConcurrentSkipListMap's forEachRemaining method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        ConcurrentSkipListMap<Integer, String>.ValueSpliterator spliterator = map.new ValueSpliterator();
        try {
            spliterator.forEachRemaining(null);
            // Expect NullPointerException
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testForEachRemainingWithValidAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Consumer<String> consumer = new Consumer<String>() {
            int count = 0;
            @Override
            public void accept(String v) {
                assert v != null : "Value should not be null";
                count++;
            }
        };
        ConcurrentSkipListMap<Integer, String>.ValueSpliterator spliterator = map.new ValueSpliterator();
        spliterator.forEachRemaining(consumer);
        // No assertion here; test passes if no exception is thrown
    }
}
