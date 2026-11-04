/*
 * @test
 * @summary Test IntPipeline.sum() for correct sum and IllegalStateException on consumed pipeline
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import java.util.stream.AbstractPipeline;
import java.util.stream.IntPipeline;
import java.util.function.Supplier;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testSumCorrectness();
        testSumOnConsumedPipelineThrows();
    }

    private static void testSumCorrectness() {
        // Create an IntStream from an int array and test sum()
        int[] values = {1, 2, 3, 4, 5};
        IntStream stream = IntStream.of(values);
        int expectedSum = 15;
        int actualSum = stream.sum();
        if (actualSum != expectedSum) {
            throw new AssertionError("Sum mismatch: expected " + expectedSum + " but got " + actualSum);
        }
    }

    private static void testSumOnConsumedPipelineThrows() {
        // Create an IntStream and consume it, then call sum() again to trigger IllegalStateException
        int[] values = {10, 20};
        IntStream stream = IntStream.of(values);
        // Consume the stream by calling count()
        long count = stream.count();
        if (count != values.length) {
            throw new AssertionError("Count mismatch: expected " + values.length + " but got " + count);
        }

        // Now sum() on the same stream should throw IllegalStateException
        boolean threw = false;
        try {
            stream.sum();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when calling sum() on consumed stream");
        }
    }
}
