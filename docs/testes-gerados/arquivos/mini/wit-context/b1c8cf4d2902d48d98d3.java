/*
 * @test
 * @summary Test LongPipeline.accept(long) for expected behavior and exception paths
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.stream.AbstractPipeline;
import java.util.stream.Sink;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    static class TestSink implements Sink<Long>, LongConsumer {
        boolean accepted = false;
        boolean downstreamAccepted = false;

        @Override
        public void accept(Long t) {
            accept(t.longValue());
        }

        @Override
        public void accept(long value) {
            accepted = true;
            if (value == 42L) {
                throw new IllegalStateException("Test exception on 42");
            }
        }

        @Override
        public void begin(long size) {}

        @Override
        public void end() {}

        @Override
        public boolean cancellationRequested() { return false; }
    }

    static class TestLongPipeline extends LongPipeline<Object> {
        private final Sink<Long> action;
        private final Sink<Long> downstream;

        TestLongPipeline(Sink<Long> action, Sink<Long> downstream) {
            super((AbstractPipeline<?, Object, LongStream>) null, 0);
            this.action = action;
            this.downstream = downstream;
        }

        @Override
        public void accept(long t) {
            action.accept(t);
            downstream.accept(t);
        }
    }

    public static void main(String[] args) {
        TestSink actionSink = new TestSink();
        TestSink downstreamSink = new TestSink();
        LongPipeline<Object> pipeline = new TestLongPipeline(actionSink, downstreamSink);

        // Test normal accept call with value not triggering exception
        pipeline.accept(1L);
        if (!actionSink.accepted) {
            throw new AssertionError("Action sink did not accept value");
        }
        if (!downstreamSink.accepted) {
            throw new AssertionError("Downstream sink did not accept value");
        }

        // Reset flags
        actionSink.accepted = false;
        downstreamSink.accepted = false;

        // Test accept call that triggers IllegalStateException in action sink
        boolean caught = false;
        try {
            pipeline.accept(42L);
        } catch (IllegalStateException e) {
            caught = true;
            if (!"Test exception on 42".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!caught) {
            throw new AssertionError("Expected IllegalStateException was not thrown");
        }

        // downstream sink should not have accepted value after exception
        if (downstreamSink.accepted) {
            throw new AssertionError("Downstream sink accepted value after exception");
        }
    }
}
