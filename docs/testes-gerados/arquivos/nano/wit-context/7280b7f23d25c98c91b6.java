/*
 * @test
 * @summary Test for LongPipeline.sum() method, covering normal and exceptional behavior.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.LongPipeline;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testSumNormal();
        testSumLinkedOrConsumed();
    }

    private static void testSumNormal() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        long result = stream.sum();
        if (result != 6L) {
            throw new AssertionError("Expected sum 6, but got " + result);
        }
    }

    private static void testSumLinkedOrConsumed() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        // Consume the stream to simulate linked or consumed state
        stream.close();
        try {
            long result = stream.sum();
            throw new AssertionError("Expected IllegalStateException, but got result: " + result);
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
