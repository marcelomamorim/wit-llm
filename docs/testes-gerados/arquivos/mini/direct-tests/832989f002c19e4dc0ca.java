/*
 * @test
 * @summary Test java.util.Spliterators.forEachRemaining(LongConsumer) method
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.Spliterator;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    static class TestSpliterator extends Spliterators.AbstractLongSpliterator {
        private final long[] data;
        private int index = 0;

        TestSpliterator(long[] data) {
            super(data.length, Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED);
            this.data = data;
        }

        @Override
        public boolean tryAdvance(java.util.function.LongConsumer action) {
            if (index < data.length) {
                action.accept(data[index++]);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        testNullActionThrows();
        testForEachRemainingConsumesAll();
    }

    private static void testNullActionThrows() {
        TestSpliterator spliterator = new TestSpliterator(new long[] {1L, 2L, 3L});
        Spliterators.ForEachRemainingLongConsumer forEachRemaining = new Spliterators.ForEachRemainingLongConsumer(spliterator);
        try {
            forEachRemaining.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testForEachRemainingConsumesAll() {
        final long[] input = {10L, 20L, 30L};
        TestSpliterator spliterator = new TestSpliterator(input);
        Spliterators.ForEachRemainingLongConsumer forEachRemaining = new Spliterators.ForEachRemainingLongConsumer(spliterator);

        final StringBuilder sb = new StringBuilder();
        LongConsumer action = new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        };

        forEachRemaining.forEachRemaining(action);

        String result = sb.toString();
        if (!result.equals("10,20,30,")) {
            throw new RuntimeException("forEachRemaining did not consume all elements: " + result);
        }
    }
}
