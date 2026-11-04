/*
 * @test
 * @summary Test DoublePipeline.accept(double) for expected behavior and exception paths
 * @run main DoublePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.stream.DoubleStream;
import java.util.stream.AbstractPipeline;
import java.util.stream.DoublePipeline;
import java.util.function.Supplier;
import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass to test accept(double) method
    static class TestDoublePipeline extends DoublePipeline<Object> {

        DoubleConsumer action;
        DoubleConsumer downstream;

        TestDoublePipeline(DoubleConsumer action, DoubleConsumer downstream) {
            super(new Supplier<Spliterator<Double>>() {
                public Spliterator<Double> get() {
                    return null;
                }
            }, 0, false);
            this.action = action;
            this.downstream = downstream;
        }

        @Override
        public void accept(double t) {
            super.accept(t);
        }

        // Implement abstract methods with minimal stubs
        @Override
        public boolean isParallel() { return false; }

        @Override
        public DoubleStream sequential() { return this; }

        @Override
        public DoubleStream parallel() { return this; }

        @Override
        public DoubleStream unordered() { return this; }

        @Override
        public DoubleStream onClose(Runnable closeHandler) { return this; }

        @Override
        public void close() { }
    }

    public static void main(String[] args) {
        // Test normal accept calls with valid DoubleConsumers
        final StringBuilder log = new StringBuilder();

        DoubleConsumer action = new DoubleConsumer() {
            public void accept(double value) {
                log.append("A" + (int)value + ";");
            }
        };

        DoubleConsumer downstream = new DoubleConsumer() {
            public void accept(double value) {
                log.append("D" + (int)value + ";");
            }
        };

        TestDoublePipeline pipeline = new TestDoublePipeline(action, downstream);

        pipeline.accept(1.0);
        pipeline.accept(2.0);

        String result = log.toString();
        if (!"A1;D1;A2;D2;".equals(result)) {
            throw new Error("Unexpected accept sequence: " + result);
        }

        // Test accept with a downstream that throws IllegalStateException as per WIT expath
        DoubleConsumer throwingDownstream = new DoubleConsumer() {
            public void accept(double value) {
                throw new IllegalStateException("Downstream accept failure");
            }
        };

        TestDoublePipeline pipelineWithException = new TestDoublePipeline(action, throwingDownstream);

        boolean caught = false;
        try {
            pipelineWithException.accept(3.0);
        } catch (IllegalStateException e) {
            caught = true;
            if (!"Downstream accept failure".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }

        if (!caught) {
            throw new Error("Expected IllegalStateException was not thrown");
        }
    }
}
