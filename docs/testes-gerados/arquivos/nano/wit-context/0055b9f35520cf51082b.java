/*
 * @test
 * @summary Test for LongPipeline.forEach method to observe behavior and exception handling.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachWithNonParallel();
        testForEachWithParallel();
    }

    private static void testForEachWithNonParallel() {
        try {
            // Create a simple source spliterator with a small range
            LongPipeline.Head source = new LongPipeline.Head(
                () -> java.util.Spliterators.spliterator(new long[]{1, 2, 3}, 0),
                0,
                false
            );
            // Call forEach with a simple consumer that sums values
            final long[] sum = {0};
            LongConsumer consumer = value -> sum[0] += value;
            source.forEach(consumer);
            System.out.println("Non-parallel forEach sum: " + sum[0]);
        } catch (Exception e) {
            System.err.println("Exception in non-parallel forEach: " + e);
        }
    }

    private static void testForEachWithParallel() {
        try {
            // Create a source spliterator with a small range
            LongPipeline.Head source = new LongPipeline.Head(
                () -> java.util.Spliterators.spliterator(new long[]{4, 5, 6}, 0),
                0,
                true
            );
            // Call super.forEach to test parallel path
            final long[] sum = {0};
            LongConsumer consumer = value -> sum[0] += value;
            source.forEach(consumer);
            System.out.println("Parallel forEach sum: " + sum[0]);
        } catch (Exception e) {
            System.err.println("Exception in parallel forEach: " + e);
        }
    }
}
