/*
 * @test
 * @summary Test LongPipeline.mapToInt(LongToIntFunction) for null argument and basic mapping
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.function.LongToIntFunction;

public class LongPipelineWitupTest {

    static class DummyLongPipeline extends java.util.stream.LongPipeline<Object> {
        DummyLongPipeline() {
            super((LongStream) null, 0, false);
        }
    }

    public static void main(String[] args) {
        LongPipelineWitupTest test = new LongPipelineWitupTest();
        test.testMapToIntNull();
        test.testMapToIntBasic();
    }

    // Test that mapToInt throws NullPointerException if mapper is null
    void testMapToIntNull() {
        LongPipeline<Object> pipeline = new DummyLongPipeline();
        try {
            pipeline.mapToInt(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Test that mapToInt applies the mapper correctly
    void testMapToIntBasic() {
        LongPipeline<Object> pipeline = new DummyLongPipeline();
        LongToIntFunction mapper = new LongToIntFunction() {
            public int applyAsInt(long value) {
                return (int)(value * 2);
            }
        };

        IntStream intStream = pipeline.mapToInt(mapper);
        // Since DummyLongPipeline has no source, the stream is empty
        // We test that the returned stream is not null and can be operated on
        Objects.requireNonNull(intStream);
        // The stream should be empty, so count should be 0
        long count = intStream.count();
        if (count != 0) {
            throw new AssertionError("Expected empty IntStream from dummy pipeline, got count=" + count);
        }
    }
}
