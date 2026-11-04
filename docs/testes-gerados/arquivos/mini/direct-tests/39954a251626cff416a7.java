/*
 * @test
 * @summary Test DoublePipeline.peek method behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass of DoublePipeline to test peek
    static class TestDoublePipeline extends java.util.stream.DoublePipeline<Double> {

        TestDoublePipeline(Spliterator<Double> source) {
            super(source, 0, false);
        }

        @Override
        public java.util.stream.Sink<Double> opWrapSink(int flags, java.util.stream.Sink<Double> sink) {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.stream.StreamShape getOutputShape() {
            return StreamShape.DOUBLE_VALUE;
        }

        @Override
        public boolean opIsStateful() {
            return false;
        }

        @Override
        public java.util.stream.Spliterator<Double> spliterator() {
            return sourceSpliterator();
        }

        private Spliterator<Double> sourceSpliterator() {
            return super.sourceStageSpliterator();
        }
    }

    public static void main(String[] args) {
        testPeekWithNullAction();
        testPeekActionCalledOnElements();
    }

    private static void testPeekWithNullAction() {
        try {
            DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
            // peek with null should throw NullPointerException
            stream.peek(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPeekActionCalledOnElements() {
        final double[] input = {1.0, 2.0, 3.0};
        final StringBuilder sb = new StringBuilder();

        DoubleStream stream = DoubleStream.of(input).peek(new DoubleConsumer() {
            public void accept(double d) {
                sb.append((int)d).append(",");
            }
        });

        // Consume the stream to trigger peek
        stream.forEach(d -> {});

        String result = sb.toString();
        if (!result.equals("1,2,3,")) {
            throw new AssertionError("Peek action was not called correctly, got: " + result);
        }
    }
}
