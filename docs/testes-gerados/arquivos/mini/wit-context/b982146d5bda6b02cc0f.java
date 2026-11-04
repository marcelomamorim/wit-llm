/*
 * @test
 * @summary Test IntPipeline.flatMap(IntFunction<? extends IntStream>) for null argument and basic behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.IntStream;
import java.util.function.IntFunction;
import java.util.Objects;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapNullArgument();
        testFlatMapBasicBehavior();
    }

    private static void testFlatMapNullArgument() {
        IntPipeline<Integer> pipeline = new IntPipeline<Integer>(
            () -> null, // Supplier<Spliterator<Integer>> - dummy, not used here
            0,
            false
        ) {
            @Override
            public int count() { return 0; }
        };

        try {
            pipeline.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFlatMapBasicBehavior() {
        // Create a simple IntPipeline from an IntStream source
        IntStream baseStream = IntStream.of(1, 2, 3);
        IntPipeline<Integer> pipeline = new IntPipeline<Integer>(
            baseStream.spliterator(),
            0,
            false
        ) {
            @Override
            public int count() { return 0; }
        };

        IntFunction<IntStream> mapper = new IntFunction<IntStream>() {
            @Override
            public IntStream apply(int value) {
                // Map each int to a stream of that int repeated twice
                return IntStream.of(value, value);
            }
        };

        IntStream resultStream = pipeline.flatMap(mapper);

        // Collect results to array and verify
        int[] results = resultStream.toArray();

        // Expect each input value repeated twice in order
        int[] expected = {1, 1, 2, 2, 3, 3};

        if (results.length != expected.length) {
            throw new AssertionError("Result length mismatch: expected " + expected.length + " but got " + results.length);
        }

        for (int i = 0; i < expected.length; i++) {
            if (results[i] != expected[i]) {
                throw new AssertionError("Result mismatch at index " + i + ": expected " + expected[i] + " but got " + results[i]);
            }
        }
    }
}
