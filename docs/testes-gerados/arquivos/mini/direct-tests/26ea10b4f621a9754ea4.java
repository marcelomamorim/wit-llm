/*
 * @test
 * @summary Test ReferencePipeline.accept method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.function.IntFunction;
import java.util.function.IntConsumer;
import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    static class TestPipeline extends ReferencePipeline<String, String> {
        IntFunction<Integer> mapper;
        IntConsumer downstream;

        TestPipeline(IntFunction<Integer> mapper, IntConsumer downstream) {
            super(null, 0, false);
            this.mapper = mapper;
            this.downstream = downstream;
        }

        @Override
        public void accept(String u) {
            downstream.accept(mapper.apply(u));
        }
    }

    public static void main(String[] args) {
        final int[] received = new int[1];
        IntFunction<Integer> mapper = new IntFunction<Integer>() {
            public Integer apply(int value) {
                return value * 2;
            }

            // Overload to accept String to satisfy ReferencePipeline.accept signature
            public Integer apply(String s) {
                return s.length();
            }
        };

        IntConsumer downstream = new IntConsumer() {
            public void accept(int value) {
                received[0] = value;
            }
        };

        TestPipeline pipeline = new TestPipeline(mapper, downstream);

        // Test with a string input
        pipeline.accept("abc");
        if (received[0] != 3) {
            throw new AssertionError("Expected 3 but got " + received[0]);
        }

        // Test with empty string
        pipeline.accept("");
        if (received[0] != 0) {
            throw new AssertionError("Expected 0 but got " + received[0]);
        }

        // Test with null input - expect NullPointerException
        boolean npeThrown = false;
        try {
            pipeline.accept(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null input");
        }
    }
}
