/*
 * @test
 * @summary Test LongPipeline.mapToInt(LongToIntFunction) for correct mapping and null handling
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongToIntFunction;

public class LongPipelineWitupTest {

    // Minimal concrete subclass of LongPipeline to test mapToInt
    static class TestLongPipeline extends LongPipeline<Object> {
        TestLongPipeline(Spliterator<Long> source) {
            super(source, 0, false);
        }

        @Override
        public void forEach(LongConsumer action) {
            Objects.requireNonNull(action);
            Spliterator<Long> spliterator = sourceSpliterator();
            spliterator.forEachRemaining(action::accept);
        }

        // Expose source spliterator for internal use
        Spliterator<Long> sourceSpliterator() {
            return super.sourceSpliterator();
        }
    }

    public static void main(String[] args) {
        testMapToIntBasic();
        testMapToIntNullMapper();
    }

    private static void testMapToIntBasic() {
        // Create a LongStream from a known array
        long[] input = {1L, 2L, 3L, 4L, 5L};
        Spliterator<Long> spliterator = Spliterators.spliterator(
                new java.util.PrimitiveIterator.OfLong() {
                    int index = 0;
                    @Override
                    public boolean hasNext() {
                        return index < input.length;
                    }
                    @Override
                    public long nextLong() {
                        return input[index++];
                    }
                }, input.length, 0);

        TestLongPipeline pipeline = new TestLongPipeline(spliterator);

        // Define a mapper that doubles the input
        LongToIntFunction mapper = new LongToIntFunction() {
            public int applyAsInt(long value) {
                return (int)(value * 2);
            }
        };

        IntStream intStream = pipeline.mapToInt(mapper);

        // Collect results to array
        int[] results = intStream.toArray();

        // Check results
        if (results.length != input.length) {
            throw new AssertionError("Result length mismatch");
        }
        for (int i = 0; i < results.length; i++) {
            int expected = (int)(input[i] * 2);
            if (results[i] != expected) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expected + ", got " + results[i]);
            }
        }
    }

    private static void testMapToIntNullMapper() {
        // Create a LongStream from empty source
        Spliterator<Long> spliterator = Spliterators.emptySpliterator();
        TestLongPipeline pipeline = new TestLongPipeline(spliterator);

        try {
            pipeline.mapToInt(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
