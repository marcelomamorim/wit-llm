/*
 * @test
 * @summary Test LongPipeline.flatMap(LongFunction) for null argument and basic behavior
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongFunction;
import java.util.Objects;

public class LongPipelineWitupTest {

    static class DummyLongPipeline extends java.util.stream.LongPipeline<Object> {
        DummyLongPipeline() {
            super((Spliterator<Long>) null, 0, false);
        }
    }

    public static void main(String[] args) {
        testFlatMapNull();
        testFlatMapBasic();
    }

    private static void testFlatMapNull() {
        LongPipeline<Object> pipeline = new DummyLongPipeline();
        try {
            pipeline.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFlatMapBasic() {
        LongPipeline<Object> pipeline = new DummyLongPipeline();
        LongFunction<LongStream> mapper = new LongFunction<LongStream>() {
            @Override
            public LongStream apply(long value) {
                return LongStream.of(value, value + 1);
            }
        };

        LongStream result = pipeline.flatMap(mapper);
        // The returned stream should produce the mapped values for a test input
        // Since DummyLongPipeline has no source, we cannot test actual stream elements,
        // but we can test that the returned stream is non-null and can be closed.
        if (result == null) {
            throw new AssertionError("flatMap returned null stream");
        }
        result.close();
    }
}
