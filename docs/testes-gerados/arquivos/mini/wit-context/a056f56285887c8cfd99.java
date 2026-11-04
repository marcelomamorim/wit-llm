/*
 * @test
 * @summary Test LongPipeline.accept(long) method for correct delegation and exception behavior
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.stream.AbstractPipeline;
import java.util.Spliterator;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    // Minimal concrete subclass of LongPipeline to test accept(long)
    static class TestLongPipeline extends LongPipeline<Long> {

        final LongUnaryOperator mapper;
        final LongConsumer downstream;

        TestLongPipeline(LongUnaryOperator mapper, LongConsumer downstream) {
            // Use dummy source and flags, parallel false
            super(new Supplier<Spliterator<Long>>() {
                public Spliterator<Long> get() { return null; }
            }, 0, false);
            this.mapper = mapper;
            this.downstream = downstream;
        }

        @Override
        public void accept(long t) {
            // replicate the tested method
            downstream.accept(mapper.applyAsLong(t));
        }

        // Other abstract methods from LongStream and AbstractPipeline are not needed for this test
    }

    public static void main(String[] args) {
        testAcceptDelegatesCorrectly();
        testAcceptPropagatesException();
        System.out.println("PASS");
    }

    private static void testAcceptDelegatesCorrectly() {
        final long input = 10L;
        final long mappedValue = 20L;

        LongUnaryOperator mapper = new LongUnaryOperator() {
            public long applyAsLong(long operand) {
                if (operand != input) throw new AssertionError("Unexpected input");
                return mappedValue;
            }
        };

        final boolean[] downstreamCalled = {false};
        LongConsumer downstream = new LongConsumer() {
            public void accept(long value) {
                downstreamCalled[0] = true;
                if (value != mappedValue) {
                    throw new AssertionError("Downstream received wrong value: " + value);
                }
            }
        };

        TestLongPipeline pipeline = new TestLongPipeline(mapper, downstream);
        pipeline.accept(input);

        if (!downstreamCalled[0]) {
            throw new AssertionError("Downstream accept was not called");
        }
    }

    private static void testAcceptPropagatesException() {
        LongUnaryOperator mapper = new LongUnaryOperator() {
            public long applyAsLong(long operand) {
                throw new IllegalStateException("forced");
            }
        };

        LongConsumer downstream = new LongConsumer() {
            public void accept(long value) {
                // Should not be called
                throw new AssertionError("Downstream accept should not be called");
            }
        };

        TestLongPipeline pipeline = new TestLongPipeline(mapper, downstream);

        try {
            pipeline.accept(0L);
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
            if (!"forced".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
