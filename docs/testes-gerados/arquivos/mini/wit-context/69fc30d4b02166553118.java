/*
 * @test
 * @summary Test Spliterators.tryAdvance(IntConsumer) for null action and normal iteration
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
        testTryAdvanceExhausted();
    }

    private static void testTryAdvanceNullAction() {
        Spliterators.IntArraySpliterator spliterator = new Spliterators.IntArraySpliterator(new int[]{1, 2, 3}, 0, 3);
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceNormal() {
        final int[] consumed = new int[3];
        final int[] index = {0};
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                consumed[index[0]++] = value;
            }
        };
        Spliterators.IntArraySpliterator spliterator = new Spliterators.IntArraySpliterator(new int[]{10, 20, 30}, 0, 3);
        boolean result1 = spliterator.tryAdvance(consumer);
        if (!result1 || consumed[0] != 10) {
            throw new RuntimeException("tryAdvance failed to consume first element");
        }
        boolean result2 = spliterator.tryAdvance(consumer);
        if (!result2 || consumed[1] != 20) {
            throw new RuntimeException("tryAdvance failed to consume second element");
        }
        boolean result3 = spliterator.tryAdvance(consumer);
        if (!result3 || consumed[2] != 30) {
            throw new RuntimeException("tryAdvance failed to consume third element");
        }
    }

    private static void testTryAdvanceExhausted() {
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                // do nothing
            }
        };
        Spliterators.IntArraySpliterator spliterator = new Spliterators.IntArraySpliterator(new int[]{5}, 0, 1);
        boolean first = spliterator.tryAdvance(consumer);
        if (!first) {
            throw new RuntimeException("tryAdvance should return true for first element");
        }
        boolean second = spliterator.tryAdvance(consumer);
        if (second) {
            throw new RuntimeException("tryAdvance should return false after elements exhausted");
        }
    }
}
