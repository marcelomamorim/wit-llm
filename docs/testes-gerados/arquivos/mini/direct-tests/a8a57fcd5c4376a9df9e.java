/*
 * @test
 * @summary Test LongPipeline.mapToDouble(LongToDoubleFunction) for basic correctness and null handling
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongToDoubleFunction;
import java.util.stream.AbstractPipeline;
import java.util.stream.DoublePipeline;
import java.util.stream.LongPipeline;

public class LongPipelineWitupTest {

    // Minimal concrete subclass of LongPipeline to allow testing
    static class TestLongPipeline extends LongPipeline<Long> {
        TestLongPipeline(Spliterator<Long> source) {
            super(source, 0, false);
        }

        TestLongPipeline(AbstractPipeline<?, Long, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        @Override
        public void forEach(LongConsumer action) {
            Objects.requireNonNull(action);
            Spliterator<Long> spliterator = sourceSpliterator();
            if (spliterator == null) {
                throw new IllegalStateException("No source spliterator");
            }
            spliterator.forEachRemaining(action::accept);
        }

        private Spliterator<Long> sourceSpliterator() {
            try {
                java.lang.reflect.Field f = AbstractPipeline.class.getDeclaredField("sourceSpliterator");
                f.setAccessible(true);
                return (Spliterator<Long>) f.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        testMapToDoubleBasic();
        testMapToDoubleNullMapper();
    }

    private static void testMapToDoubleBasic() {
        // Create a LongPipeline from a known source
        Long[] longs = new Long[] {1L, 2L, 3L};
        Spliterator<Long> spliterator = Spliterators.spliterator(longs, 0);
        TestLongPipeline pipeline = new TestLongPipeline(spliterator);

        // Define a mapper that converts long to double by multiplying by 2.5
        LongToDoubleFunction mapper = new LongToDoubleFunction() {
            public double applyAsDouble(long value) {
                return value * 2.5;
            }
        };

        DoubleStream ds = pipeline.mapToDouble(mapper);

        // Collect results to array and verify
        double[] results = ds.toArray();

        if (results.length != longs.length) {
            throw new AssertionError("Result length mismatch");
        }

        for (int i = 0; i < results.length; i++) {
            double expected = longs[i] * 2.5;
            if (Double.compare(results[i], expected) != 0) {
                throw new AssertionError("Element " + i + " expected " + expected + " but got " + results[i]);
            }
        }
    }

    private static void testMapToDoubleNullMapper() {
        Long[] longs = new Long[] {1L};
        Spliterator<Long> spliterator = Spliterators.spliterator(longs, 0);
        TestLongPipeline pipeline = new TestLongPipeline(spliterator);

        try {
            pipeline.mapToDouble(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
