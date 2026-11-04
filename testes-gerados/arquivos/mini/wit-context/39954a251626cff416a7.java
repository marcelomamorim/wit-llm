/*
 * @test
 * @summary Test DoublePipeline.peek method for null argument and normal behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.DoublePipeline;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass of DoublePipeline to allow testing
    static class TestDoublePipeline extends DoublePipeline<Object> {
        TestDoublePipeline() {
            super((Spliterator<Double>) null, 0, false);
        }

        @Override
        public void forEach(DoubleConsumer action) {
            // no-op
        }
    }

    public static void main(String[] args) {
        testPeekNullAction();
        testPeekNormalBehavior();
        System.out.println("PASS");
    }

    private static void testPeekNullAction() {
        DoublePipeline<Object> pipeline = new TestDoublePipeline();
        try {
            pipeline.peek(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPeekNormalBehavior() {
        DoublePipeline<Object> pipeline = new TestDoublePipeline();
        final double[] seen = new double[1];
        DoubleConsumer action = new DoubleConsumer() {
            @Override
            public void accept(double value) {
                seen[0] = value;
            }
        };

        DoubleStream peeked = pipeline.peek(action);
        if (peeked == null) {
            throw new AssertionError("peek returned null");
        }

        // The returned stream is a StatelessOp anonymous subclass, cannot easily test internals
        // But we can test that peek returns a non-null stream and does not throw for non-null action
    }
}
