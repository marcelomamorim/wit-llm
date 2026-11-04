/*
 * @test
 * @summary Test DoublePipeline.accept(double) method behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.function.DoubleConsumer;

public class DoublePipelineWitupTest {

    static class TestDoublePipeline extends java.util.stream.DoublePipeline<Object> {

        DoubleConsumer action;
        DoubleConsumer downstream;

        TestDoublePipeline(DoubleConsumer action, DoubleConsumer downstream) {
            super(null, 0, false);
            this.action = action;
            this.downstream = downstream;
        }

        @Override
        public void accept(double t) {
            action.accept(t);
            downstream.accept(t);
        }
    }

    public static void main(String[] args) {
        final StringBuilder log = new StringBuilder();

        DoubleConsumer action = new DoubleConsumer() {
            public void accept(double t) {
                log.append("A").append(t).append(",");
            }
        };

        DoubleConsumer downstream = new DoubleConsumer() {
            public void accept(double t) {
                log.append("D").append(t).append(",");
            }
        };

        TestDoublePipeline pipeline = new TestDoublePipeline(action, downstream);

        pipeline.accept(1.5);
        pipeline.accept(-2.0);
        pipeline.accept(0.0);

        String expected = "A1.5,D1.5,A-2.0,D-2.0,A0.0,D0.0,";
        if (!log.toString().equals(expected)) {
            throw new AssertionError("Unexpected accept calls sequence: " + log);
        }
    }
}
