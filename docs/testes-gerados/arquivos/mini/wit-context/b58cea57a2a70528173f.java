/*
 * @test
 * @summary Test LongPipeline.limit(long) method for valid and invalid inputs
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.SliceOps;

public class LongPipelineWitupTest {

    // Minimal stub class to allow calling limit on LongPipeline
    // We create a minimal LongPipeline subclass to call limit
    static class TestLongPipeline extends java.util.stream.LongPipeline<Object> {
        TestLongPipeline() {
            super((java.util.stream.AbstractPipeline<?, Object, ?>) null, 0);
        }

        @Override
        public java.util.stream.Spliterator.OfLong spliterator() {
            return java.util.Spliterators.emptyLongSpliterator();
        }

        @Override
        public boolean isParallel() {
            return false;
        }

        @Override
        public LongStream limit(long maxSize) {
            return super.limit(maxSize);
        }
    }

    public static void main(String[] args) {
        TestLongPipeline pipeline = new TestLongPipeline();

        // Test limit with valid maxSize values
        LongStream stream1 = pipeline.limit(0L);
        if (stream1 == null) {
            throw new AssertionError("limit(0) returned null");
        }

        LongStream stream2 = pipeline.limit(10L);
        if (stream2 == null) {
            throw new AssertionError("limit(10) returned null");
        }

        // Test limit with maxSize = Long.MAX_VALUE
        LongStream stream3 = pipeline.limit(Long.MAX_VALUE);
        if (stream3 == null) {
            throw new AssertionError("limit(Long.MAX_VALUE) returned null");
        }

        // Test limit with negative maxSize should throw IllegalArgumentException
        boolean threw = false;
        try {
            pipeline.limit(-1L);
        } catch (IllegalArgumentException e) {
            threw = true;
            if (!e.getMessage().equals("-1")) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new AssertionError("limit(-1) did not throw IllegalArgumentException");
        }

        // Test limit with negative maxSize large negative value
        threw = false;
        try {
            pipeline.limit(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            threw = true;
            if (!e.getMessage().equals(Long.toString(Long.MIN_VALUE))) {
                throw new AssertionError("Exception message mismatch for Long.MIN_VALUE: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new AssertionError("limit(Long.MIN_VALUE) did not throw IllegalArgumentException");
        }
    }
}
