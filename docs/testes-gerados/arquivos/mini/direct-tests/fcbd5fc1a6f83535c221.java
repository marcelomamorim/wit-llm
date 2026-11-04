/*
 * @test
 * @summary Test IntPipeline.accept(int) method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.DoubleConsumer;

public class IntPipelineWitupTest {

    static class TestIntPipeline extends IntPipeline<Object> {
        DoubleConsumer downstream;

        TestIntPipeline(DoubleConsumer downstream) {
            super((Spliterator<Integer>) null, 0, false);
            this.downstream = downstream;
        }

        @Override
        public void accept(int t) {
            downstream.accept((double) t);
        }
    }

    public static void main(String[] args) {
        final StringBuilder sb = new StringBuilder();
        DoubleConsumer dc = new DoubleConsumer() {
            public void accept(double value) {
                sb.append((int) value).append(",");
            }
        };

        TestIntPipeline pipeline = new TestIntPipeline(dc);

        pipeline.accept(5);
        pipeline.accept(-10);
        pipeline.accept(0);

        String result = sb.toString();
        if (!result.equals("5,-10,0,")) {
            throw new AssertionError("Unexpected downstream accept calls: " + result);
        }
    }
}
