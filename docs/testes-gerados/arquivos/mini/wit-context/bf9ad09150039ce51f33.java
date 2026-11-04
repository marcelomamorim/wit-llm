/*
 * @test
 * @summary Test Spliterators.tryAdvance(DoubleConsumer) for normal and exceptional behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidAction();
        testTryAdvanceWhenIndexOutOfBounds();
    }

    private static void testTryAdvanceWithNullAction() {
        final double[] array = {1.0, 2.0};
        Spliterators.DoubleArraySpliterator spliterator = new Spliterators.DoubleArraySpliterator(array, 0, array.length, 0);
        try {
            spliterator.tryAdvance(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testTryAdvanceWithValidAction() {
        final double[] array = {10.0, 20.0};
        Spliterators.DoubleArraySpliterator spliterator = new Spliterators.DoubleArraySpliterator(array, 0, array.length, 0);
        final double[] consumed = new double[2];
        final int[] index = {0};
        DoubleConsumer action = new DoubleConsumer() {
            public void accept(double value) {
                consumed[index[0]++] = value;
            }
        };

        boolean result1 = spliterator.tryAdvance(action);
        if (!result1) throw new Error("tryAdvance should return true when element available");
        if (consumed[0] != 10.0) throw new Error("First consumed value incorrect");

        boolean result2 = spliterator.tryAdvance(action);
        if (!result2) throw new Error("tryAdvance should return true when element available");
        if (consumed[1] != 20.0) throw new Error("Second consumed value incorrect");
    }

    private static void testTryAdvanceWhenIndexOutOfBounds() {
        final double[] array = {5.0};
        Spliterators.DoubleArraySpliterator spliterator = new Spliterators.DoubleArraySpliterator(array, 0, 1, 0);
        final int[] count = {0};
        DoubleConsumer action = new DoubleConsumer() {
            public void accept(double value) {
                count[0]++;
            }
        };

        // Consume the only element
        boolean first = spliterator.tryAdvance(action);
        if (!first) throw new Error("tryAdvance should return true for first element");
        if (count[0] != 1) throw new Error("Action should have been called once");

        // Now no elements left
        boolean second = spliterator.tryAdvance(action);
        if (second) throw new Error("tryAdvance should return false when no elements left");
        if (count[0] != 1) throw new Error("Action should not be called when no elements left");
    }
}
