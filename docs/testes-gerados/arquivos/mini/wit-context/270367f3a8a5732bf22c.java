/*
 * @test
 * @summary Test java.util.Spliterators.tryAdvance(LongConsumer) for null action and normal iteration
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
        Spliterators.LongIteratorSpliterator spliterator = new Spliterators.LongIteratorSpliterator(new long[]{1L, 2L, 3L}, 0, 3);
        try {
            spliterator.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testTryAdvanceNormal() {
        final long[] values = {10L, 20L};
        Spliterators.LongIteratorSpliterator spliterator = new Spliterators.LongIteratorSpliterator(values, 0, values.length);
        final long[] consumed = new long[values.length];
        final int[] index = {0};

        LongConsumer action = new LongConsumer() {
            public void accept(long value) {
                consumed[index[0]++] = value;
            }
        };

        boolean advanced1 = spliterator.tryAdvance(action);
        if (!advanced1) {
            throw new AssertionError("Expected tryAdvance to return true on first element");
        }
        if (consumed[0] != values[0]) {
            throw new AssertionError("Expected first consumed value to be " + values[0] + " but was " + consumed[0]);
        }

        boolean advanced2 = spliterator.tryAdvance(action);
        if (!advanced2) {
            throw new AssertionError("Expected tryAdvance to return true on second element");
        }
        if (consumed[1] != values[1]) {
            throw new AssertionError("Expected second consumed value to be " + values[1] + " but was " + consumed[1]);
        }

        boolean advanced3 = spliterator.tryAdvance(action);
        if (advanced3) {
            throw new AssertionError("Expected tryAdvance to return false after all elements consumed");
        }
    }
}
