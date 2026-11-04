/*
 * @test
 * @summary Test IntPipeline.mapToLong with valid and null mapper
 * @run main IntPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.LongStream;
import java.util.stream.IntStream;
import java.util.function.IntToLongFunction;

public class IntPipelineWitupTest {

    static class SimpleIntPipeline extends IntPipeline<Integer> {
        SimpleIntPipeline(IntStream upstream) {
            super(upstream.spliterator(), 0, false);
        }
    }

    public static void main(String[] args) {
        // Test 1: mapToLong with a simple mapper
        IntStream baseStream = IntStream.of(1, 2, 3);
        IntPipeline<Integer> pipeline = new SimpleIntPipeline(baseStream);

        IntToLongFunction mapper = new IntToLongFunction() {
            public long applyAsLong(int value) {
                return value * 10L;
            }
        };

        LongStream longStream = pipeline.mapToLong(mapper);
        long[] results = longStream.toArray();

        if (results.length != 3 || results[0] != 10L || results[1] != 20L || results[2] != 30L) {
            throw new AssertionError("mapToLong did not produce expected results");
        }

        // Test 2: mapToLong with null mapper should throw NullPointerException
        try {
            pipeline.mapToLong(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
