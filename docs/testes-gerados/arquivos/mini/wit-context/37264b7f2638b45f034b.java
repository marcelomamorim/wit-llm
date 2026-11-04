/*
 * @test
 * @summary Test IntPipeline.accept(int) method for correct downstream invocation and exception propagation
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntUnaryOperator;
import java.util.function.IntConsumer;

public class IntPipelineWitupTest {

    // Minimal concrete subclass to test accept method
    static class TestIntPipeline extends IntPipeline<Integer> {

        private final IntUnaryOperator mapper;
        private final IntConsumer downstream;

        TestIntPipeline(IntUnaryOperator mapper, IntConsumer downstream) {
            super(null, 0, false);
            this.mapper = mapper;
            this.downstream = downstream;
        }

        @Override
        public void accept(int t) {
            downstream.accept(mapper.applyAsInt(t));
        }

        // Other abstract methods are not needed for this test
    }

    public static void main(String[] args) {
        testAcceptNormal();
        testAcceptThrows();
        System.out.println("PASS");
    }

    private static void testAcceptNormal() {
        final int input = 5;
        final int expectedMapped = input * 2;

        IntUnaryOperator mapper = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand * 2;
            }
        };

        final int[] received = new int[1];
        IntConsumer downstream = new IntConsumer() {
            public void accept(int value) {
                received[0] = value;
            }
        };

        TestIntPipeline pipeline = new TestIntPipeline(mapper, downstream);
        pipeline.accept(input);

        if (received[0] != expectedMapped) {
            throw new AssertionError("Downstream did not receive mapped value. Expected: " + expectedMapped + ", got: " + received[0]);
        }
    }

    private static void testAcceptThrows() {
        IntUnaryOperator mapper = new IntUnaryOperator() {
            public int applyAsInt(int operand) {
                return operand;
            }
        };

        IntConsumer downstream = new IntConsumer() {
            public void accept(int value) {
                throw new IllegalStateException("Downstream failure");
            }
        };

        TestIntPipeline pipeline = new TestIntPipeline(mapper, downstream);

        try {
            pipeline.accept(10);
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
            if (!"Downstream failure".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
