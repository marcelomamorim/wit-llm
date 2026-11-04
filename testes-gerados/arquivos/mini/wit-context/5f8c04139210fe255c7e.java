/*
 * @test
 * @summary Test DoublePipeline.mapToLong method for null argument and basic mapping
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.function.DoubleToLongFunction;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamShape;
import java.util.stream.StreamOpFlag;
import java.util.stream.LongPipeline;
import java.util.stream.Sink;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass of DoublePipeline to allow testing
    static class TestDoublePipeline extends DoublePipeline<Double> {
        TestDoublePipeline(Spliterator<Double> source) {
            super(source, 0, false);
        }

        TestDoublePipeline(AbstractPipeline<?, Double, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        @Override
        public boolean isParallel() {
            return false;
        }

        @Override
        public DoubleStream sequential() {
            throw new UnsupportedOperationException();
        }

        @Override
        public DoubleStream parallel() {
            throw new UnsupportedOperationException();
        }

        @Override
        public DoubleStream unordered() {
            throw new UnsupportedOperationException();
        }

        @Override
        public DoubleStream onClose(Runnable closeHandler) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEach(DoubleConsumer action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachOrdered(DoubleConsumer action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PrimitiveIterator.OfDouble iterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Spliterator.OfDouble spliterator() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        testMapToLongNull();
        testMapToLongBasic();
        System.out.println("All tests passed.");
    }

    private static void testMapToLongNull() {
        // Create dummy DoublePipeline instance
        DoubleStream dummyStream = DoubleStream.of(1.0);
        DoublePipeline<Double> pipeline = new TestDoublePipeline(dummyStream.spliterator());

        try {
            pipeline.mapToLong(null);
            throw new RuntimeException("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testMapToLongBasic() {
        DoubleStream baseStream = DoubleStream.of(1.5, 2.5, 3.5);
        DoublePipeline<Double> pipeline = new TestDoublePipeline(baseStream.spliterator());

        DoubleToLongFunction mapper = new DoubleToLongFunction() {
            @Override
            public long applyAsLong(double value) {
                return (long) (value * 2);
            }
        };

        LongStream longStream = pipeline.mapToLong(mapper);

        long[] results = longStream.toArray();

        if (results.length != 3) {
            throw new RuntimeException("Expected 3 elements in result");
        }
        if (results[0] != 3L || results[1] != 5L || results[2] != 7L) {
            throw new RuntimeException("Unexpected mapped values: " + java.util.Arrays.toString(results));
        }
    }
}
