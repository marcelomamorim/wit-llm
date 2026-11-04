/*
 * @test
 * @summary Test IntPipeline.accept(int) method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.function.IntUnaryOperator;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    static class DummyIntPipeline extends java.util.stream.IntPipeline<Integer> {

        final IntUnaryOperator mapper;
        final IntConsumer downstream;

        DummyIntPipeline(IntUnaryOperator mapper, IntConsumer downstream) {
            super((java.util.stream.AbstractPipeline<?, Integer, ?>) null, 0);
            this.mapper = mapper;
            this.downstream = downstream;
        }

        @Override
        public void accept(int t) {
            downstream.accept(mapper.applyAsDouble(t));
        }
    }

    public static void main(String[] args) {
        // Test 1: simple identity mapping
        final int[] result1 = new int[1];
        IntUnaryOperator identity = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand;
            }
        };
        IntConsumer consumer1 = new IntConsumer() {
            public void accept(int value) {
                result1[0] = value;
            }
        };
        DummyIntPipeline pipeline1 = new DummyIntPipeline(identity, consumer1);
        pipeline1.accept(42);
        if (result1[0] != 42) {
            throw new AssertionError("Test 1 failed: expected 42 but got " + result1[0]);
        }

        // Test 2: mapping doubles the input
        final int[] result2 = new int[1];
        IntUnaryOperator doubler = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand * 2;
            }
        };
        IntConsumer consumer2 = new IntConsumer() {
            public void accept(int value) {
                result2[0] = value;
            }
        };
        DummyIntPipeline pipeline2 = new DummyIntPipeline(doubler, consumer2);
        pipeline2.accept(21);
        if (result2[0] != 42) {
            throw new AssertionError("Test 2 failed: expected 42 but got " + result2[0]);
        }

        // Test 3: mapping returns negative value
        final int[] result3 = new int[1];
        IntUnaryOperator negater = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return -operand;
            }
        };
        IntConsumer consumer3 = new IntConsumer() {
            public void accept(int value) {
                result3[0] = value;
            }
        };
        DummyIntPipeline pipeline3 = new DummyIntPipeline(negater, consumer3);
        pipeline3.accept(10);
        if (result3[0] != -10) {
            throw new AssertionError("Test 3 failed: expected -10 but got " + result3[0]);
        }

        // Test 4: mapping returns zero
        final int[] result4 = new int[1];
        IntUnaryOperator zeroer = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return 0;
            }
        };
        IntConsumer consumer4 = new IntConsumer() {
            public void accept(int value) {
                result4[0] = value;
            }
        };
        DummyIntPipeline pipeline4 = new DummyIntPipeline(zeroer, consumer4);
        pipeline4.accept(12345);
        if (result4[0] != 0) {
            throw new AssertionError("Test 4 failed: expected 0 but got " + result4[0]);
        }

        System.out.println("All tests passed.");
    }
}
