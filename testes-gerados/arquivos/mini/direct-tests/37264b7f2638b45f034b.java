/*
 * @test
 * @summary Test IntPipeline.accept(int) method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.function.IntUnaryOperator;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    static class TestIntPipeline extends java.util.stream.IntPipeline<Integer> {

        final IntUnaryOperator mapper;
        final IntConsumer downstream;

        TestIntPipeline(IntUnaryOperator mapper, IntConsumer downstream) {
            super(null, 0, false);
            this.mapper = mapper;
            this.downstream = downstream;
        }

        @Override
        public void accept(int t) {
            downstream.accept(mapper.applyAsInt(t));
        }
    }

    public static void main(String[] args) {
        final int[] result = new int[1];

        // Mapper doubles the input
        IntUnaryOperator mapper = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand * 2;
            }
        };

        // Downstream consumer stores the accepted value
        IntConsumer downstream = new IntConsumer() {
            public void accept(int value) {
                result[0] = value;
            }
        };

        TestIntPipeline pipeline = new TestIntPipeline(mapper, downstream);

        // Test with positive input
        pipeline.accept(10);
        if (result[0] != 20) {
            throw new AssertionError("Expected 20 but got " + result[0]);
        }

        // Test with zero input
        pipeline.accept(0);
        if (result[0] != 0) {
            throw new AssertionError("Expected 0 but got " + result[0]);
        }

        // Test with negative input
        pipeline.accept(-5);
        if (result[0] != -10) {
            throw new AssertionError("Expected -10 but got " + result[0]);
        }
    }
}
