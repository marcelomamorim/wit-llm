/*
 * @test
 * @summary Test LongPipeline.accept(long) method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.function.DoubleConsumer;

public class LongPipelineWitupTest {

    static class TestLongPipeline extends LongPipeline<Object> {

        DoubleConsumer downstream;

        TestLongPipeline(DoubleConsumer downstream) {
            super((Spliterator<Long>) null, 0, false);
            this.downstream = downstream;
        }

        @Override
        public void accept(long t) {
            downstream.accept((double) t);
        }
    }

    public static void main(String[] args) {
        final StringBuilder sb = new StringBuilder();
        DoubleConsumer dc = new DoubleConsumer() {
            public void accept(double value) {
                sb.append("Value:").append(value);
            }
        };

        TestLongPipeline pipeline = new TestLongPipeline(dc);

        pipeline.accept(123L);
        if (!"Value:123.0".equals(sb.toString())) {
            throw new AssertionError("Expected 'Value:123.0' but got '" + sb.toString() + "'");
        }

        sb.setLength(0);
        pipeline.accept(-456L);
        if (!"Value:-456.0".equals(sb.toString())) {
            throw new AssertionError("Expected 'Value:-456.0' but got '" + sb.toString() + "'");
        }

        sb.setLength(0);
        pipeline.accept(0L);
        if (!"Value:0.0".equals(sb.toString())) {
            throw new AssertionError("Expected 'Value:0.0' but got '" + sb.toString() + "'");
        }
    }
}
