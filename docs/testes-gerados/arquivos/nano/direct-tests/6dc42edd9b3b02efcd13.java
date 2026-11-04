/*
 * @test
 * @summary Test for Spliterators.forEachRemaining(LongConsumer)
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        long[] array = {1L, 2L, 3L};
        class Collector implements LongConsumer {
            public long sum = 0;
            public void accept(long value) {
                sum += value;
            }
        }
        Collector collector = new Collector();
        // Create a Spliterator with the array
        java.util.Spliterator.OfLong spliterator = java.util.Spliterators.spliterator(array, java.util.Spliterator.SIZED | java.util.Spliterator.SUBSIZED);
        // Call forEachRemaining
        spliterator.forEachRemaining(collector);
        // Verify the sum
        if (collector.sum != 6L) {
            throw new AssertionError("Expected sum 6, but got " + collector.sum);
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        long[] array = {1L, 2L};
        java.util.Spliterator.OfLong spliterator = java.util.Spliterators.spliterator(array, java.util.Spliterator.SIZED | java.util.Spliterator.SUBSIZED);
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
