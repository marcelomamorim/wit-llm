/*
 * @test
 * @summary Test for IntPipeline.max() method behavior.
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntPipeline;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMaxWithValues();
        testMaxEmptyStream();
        testMaxLinkedOrConsumed();
    }

    private static void testMaxWithValues() {
        IntPipeline pipeline = IntPipeline.of(new int[]{1, 3, 2});
        OptionalInt result = pipeline.max();
        if (!result.isPresent() || result.getAsInt() != 3) {
            throw new AssertionError("Expected max to be 3, but got " + result);
        }
    }

    private static void testMaxEmptyStream() {
        IntPipeline emptyPipeline = IntPipeline.of(new int[] {});
        OptionalInt result = emptyPipeline.max();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty result, but got " + result);
        }
    }

    private static void testMaxLinkedOrConsumed() {
        int[] data = {1, 2, 3};
        IntPipeline pipeline = IntPipeline.of(data);
        // Simulate linked or consumed state by consuming the stream
        pipeline.forEach(i -> {});
        try {
            OptionalInt result = pipeline.max();
            if (result.isPresent()) {
                throw new AssertionError("Expected empty or exception due to linked/consumed, but got " + result);
            }
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
