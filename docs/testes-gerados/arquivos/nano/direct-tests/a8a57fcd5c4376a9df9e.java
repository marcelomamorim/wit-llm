/*
 * @test
 * @summary Tests for LongPipeline.mapToDouble method.
 * @run main LongPipelineWitupTest
 */
import java.util.stream.LongPipeline;
import java.util.stream.DoubleStream;
import java.util.function.LongToDoubleFunction;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testMapToDoubleWithNull();
        testMapToDoubleBasic();
    }

    static void testMapToDoubleWithNull() {
        LongPipeline pipeline = new LongPipeline() {
            @Override
            public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
                return null;
            }
        };
        try {
            pipeline.mapToDouble(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testMapToDoubleBasic() {
        LongPipeline pipeline = new LongPipeline() {
            @Override
            public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
                return new DoubleStream() {
                    @Override
                    public DoubleStream map(DoubleUnaryOperator mapper) { return null; }
                    @Override
                    public DoubleStream flatMap(DoubleMapper mapper) { return null; }
                    @Override
                    public DoubleStream filter(DoublePredicate predicate) { return null; }
                    @Override
                    public void forEach(DoubleConsumer action) {}
                    @Override
                    public double sum() { return 42.0; }
                    @Override
                    public OptionalDouble average() { return OptionalDouble.of(42.0); }
                    @Override
                    public double min() { return 42.0; }
                    @Override
                    public double max() { return 42.0; }
                    @Override
                    public long count() { return 1; }
                    @Override
                    public DoubleStream distinct() { return this; }
                    @Override
                    public DoubleStream sorted() { return this; }
                    @Override
                    public DoubleStream peek(DoubleConsumer action) { return this; }
                    @Override
                    public DoubleStream limit(long maxSize) { return this; }
                    @Override
                    public DoubleStream skip(long n) { return this; }
                    @Override
                    public double[] toArray() { return new double[]{42.0}; }
                    @Override
                    public java.util.Spliterator.OfDouble spliterator() { return null; }
                    @Override
                    public java.util.stream.BaseStream<Double, DoubleStream> sequential() { return this; }
                    @Override
                    public java.util.stream.BaseStream<Double, DoubleStream> parallel() { return this; }
                    @Override
                    public java.util.stream.DoubleStream unordered() { return this; }
                    @Override
                    public java.util.stream.DoubleStream onClose(Runnable closeHandler) { return this; }
                    @Override
                    public void close() {}
                };
            }
        };
        LongToDoubleFunction mapper = t -> t * 2.0;
        DoubleStream result = pipeline.mapToDouble(mapper);
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        if (result.sum() != 84.0) {
            throw new AssertionError("Unexpected sum: " + result.sum());
        }
    }
}
