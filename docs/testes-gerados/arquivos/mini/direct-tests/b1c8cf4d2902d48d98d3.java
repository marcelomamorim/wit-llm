/*
 * @test
 * @summary Test LongPipeline.accept(long) method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    static class TestLongPipeline extends LongPipeline<Object> {
        LongConsumer action;
        LongConsumer downstream;

        TestLongPipeline(LongConsumer action, LongConsumer downstream) {
            super((Spliterator<Long>) null, 0, false);
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
        final StringBuilder log = new StringBuilder();

        LongConsumer action = new LongConsumer() {
            public void accept(long value) {
                log.append("A").append(value).append(",");
            }
        };

        LongConsumer downstream = new LongConsumer() {
            public void accept(long value) {
                log.append("D").append(value).append(",");
            }
        };

        TestLongPipeline pipeline = new TestLongPipeline(action, downstream);

        // Test with a positive long
        pipeline.accept(42L);
        if (!log.toString().equals("A42,D42,")) {
            throw new AssertionError("Unexpected log content: " + log);
        }

        // Clear log and test with zero
        log.setLength(0);
        pipeline.accept(0L);
        if (!log.toString().equals("A0,D0,")) {
            throw new AssertionError("Unexpected log content: " + log);
        }

        // Clear log and test with negative long
        log.setLength(0);
        pipeline.accept(-7L);
        if (!log.toString().equals("A-7,D-7,")) {
            throw new AssertionError("Unexpected log content: " + log);
        }
    }
}
