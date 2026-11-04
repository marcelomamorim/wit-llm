/*
 * @test
 * @summary Test java.util.Spliterators.tryAdvance(LongConsumer) for null action and normal behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
    }

    private static void testTryAdvanceNullAction() {
        Spliterators.LongArraySpliterator spliterator = new Spliterators.LongArraySpliterator(new long[]{1L, 2L}, 0, 2);
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testTryAdvanceNormal() {
        long[] array = {10L, 20L};
        Spliterators.LongArraySpliterator spliterator = new Spliterators.LongArraySpliterator(array, 0, array.length);
        final long[] consumed = new long[2];
        final int[] index = {0};
        LongConsumer consumer = new LongConsumer() {
            public void accept(long value) {
                consumed[index[0]++] = value;
            }
        };

        boolean advanced1 = spliterator.tryAdvance(consumer);
        if (!advanced1) {
            throw new RuntimeException("tryAdvance should return true on first element");
        }
        if (consumed[0] != 10L) {
            throw new RuntimeException("tryAdvance did not consume first element correctly");
        }

        boolean advanced2 = spliterator.tryAdvance(consumer);
        if (!advanced2) {
            throw new RuntimeException("tryAdvance should return true on second element");
        }
        if (consumed[1] != 20L) {
            throw new RuntimeException("tryAdvance did not consume second element correctly");
        }

        boolean advanced3 = spliterator.tryAdvance(consumer);
        if (advanced3) {
            throw new RuntimeException("tryAdvance should return false after all elements consumed");
        }
    }
}
