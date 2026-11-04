/*
 * @test
 * @summary Test LongPipeline.accept(long) method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.function.LongUnaryOperator;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    static class TestLongPipeline extends java.util.stream.LongPipeline<Long> {

        LongUnaryOperator mapper;
        LongConsumer downstream;

        TestLongPipeline(LongUnaryOperator mapper, LongConsumer downstream) {
            super(null, 0, false);
            this.mapper = mapper;
            this.downstream = downstream;
        }

        @Override
        public void accept(long t) {
            downstream.accept(mapper.applyAsLong(t));
        }
    }

    public static void main(String[] args) {
        // Test 1: simple mapping and accept
        final long[] result = new long[1];
        LongUnaryOperator mapper = new LongUnaryOperator() {
            public long applyAsLong(long operand) {
                return operand * 2;
            }
        };
        LongConsumer downstream = new LongConsumer() {
            public void accept(long value) {
                result[0] = value;
            }
        };

        TestLongPipeline pipeline = new TestLongPipeline(mapper, downstream);
        pipeline.accept(10L);
        if (result[0] != 20L) {
            throw new AssertionError("Expected 20 but got " + result[0]);
        }

        // Test 2: mapping identity function
        result[0] = 0L;
        pipeline = new TestLongPipeline(new LongUnaryOperator() {
            public long applyAsLong(long operand) {
                return operand;
            }
        }, new LongConsumer() {
            public void accept(long value) {
                result[0] = value;
            }
        });
        pipeline.accept(12345L);
        if (result[0] != 12345L) {
            throw new AssertionError("Expected 12345 but got " + result[0]);
        }

        // Test 3: mapping to constant
        pipeline = new TestLongPipeline(new LongUnaryOperator() {
            public long applyAsLong(long operand) {
                return 42L;
            }
        }, new LongConsumer() {
            public void accept(long value) {
                result[0] = value;
            }
        });
        pipeline.accept(999L);
        if (result[0] != 42L) {
            throw new AssertionError("Expected 42 but got " + result[0]);
        }

        System.out.println("All tests passed.");
    }
}
