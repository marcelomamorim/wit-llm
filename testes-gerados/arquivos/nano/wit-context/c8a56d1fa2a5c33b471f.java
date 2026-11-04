/*
 * @test
 * @summary Test for ReferencePipeline.reduce method to verify exception path handling.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testReduceLinkedOrConsumedThrowsIllegalStateException();
    }

    private static void testReduceLinkedOrConsumedThrowsIllegalStateException() {
        try {
            // Prepare dummy parameters
            Integer identity = 0;
            BiFunction<Integer, String, Integer> accumulator = (a, b) -> a + b.length();
            BinaryOperator<Integer> combiner = (a, b) -> a + b;

            // Call reduce with dummy parameters
            ReferencePipeline.<String>empty()
                .reduce(identity, accumulator, combiner);

            // If no exception, test fails
            System.err.println("Test failed: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            // Expected exception caught
            System.out.println("Test passed: IllegalStateException was correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception type: " + t);
        }
    }
}
