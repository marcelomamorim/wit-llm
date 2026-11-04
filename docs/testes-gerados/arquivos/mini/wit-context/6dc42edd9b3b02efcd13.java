/*
 * @test
 * @summary Test java.util.Spliterators.forEachRemaining(LongConsumer) for NPE and normal behavior
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    private static void testForEachRemainingNullAction() {
        // Create a LongArraySpliterator with a small array
        long[] array = {1L, 2L, 3L};
        Spliterators.LongArraySpliterator spliterator = new Spliterators.LongArraySpliterator(array, 0, array.length, 0);

        // Test that passing null action throws NullPointerException
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testForEachRemainingNormal() {
        long[] array = {10L, 20L, 30L};
        Spliterators.LongArraySpliterator spliterator = new Spliterators.LongArraySpliterator(array, 0, array.length, 0);

        final StringBuilder sb = new StringBuilder();
        LongConsumer action = new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        };

        spliterator.forEachRemaining(action);

        String result = sb.toString();
        if (!result.equals("10,20,30,")) {
            throw new RuntimeException("Unexpected forEachRemaining output: " + result);
        }

        // After forEachRemaining, index should be at fence, so no more elements
        final StringBuilder sb2 = new StringBuilder();
        spliterator.forEachRemaining(new LongConsumer() {
            public void accept(long value) {
                sb2.append(value);
            }
        });
        if (sb2.length() != 0) {
            throw new RuntimeException("forEachRemaining after exhaustion should not call accept");
        }
    }
}
