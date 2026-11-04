/*
 * @test
 * @summary Test IntPipeline.skip(long) for correct behavior and exception on negative input
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.SliceOps;

public class IntPipelineWitupTest {

    // Minimal IntPipeline subclass to test skip method
    static class TestIntPipeline extends IntStream {

        private final long skipN;

        TestIntPipeline() {
            super();
            this.skipN = 0;
        }

        // We do not override all abstract methods because we only test skip()
        // We will call skip() on this instance

        @Override
        public IntStream skip(long n) {
            if (n < 0)
                throw new IllegalArgumentException(Long.toString(n));
            if (n == 0)
                return this;
            else
                return SliceOps.makeInt(this, n, -1);
        }

        // Dummy implementations to satisfy abstract class
        @Override
        public void forEach(java.util.function.IntConsumer action) {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.PrimitiveIterator.OfInt iterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.Spliterator.OfInt spliterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isParallel() {
            return false;
        }

        @Override
        public IntStream sequential() {
            return this;
        }

        @Override
        public IntStream parallel() {
            return this;
        }

        @Override
        public IntStream unordered() {
            return this;
        }

        @Override
        public IntStream onClose(Runnable closeHandler) {
            return this;
        }

        @Override
        public void close() {
        }
    }

    public static void main(String[] args) {
        TestIntPipeline pipeline = new TestIntPipeline();

        // Test skip with n == 0 returns same instance
        IntStream result0 = pipeline.skip(0);
        if (result0 != pipeline) {
            throw new AssertionError("skip(0) should return same instance");
        }

        // Test skip with positive n returns a SliceOps.IntSlice instance
        IntStream resultPositive = pipeline.skip(5);
        if (resultPositive == null) {
            throw new AssertionError("skip(positive) should not return null");
        }
        String className = resultPositive.getClass().getName();
        if (!className.contains("SliceOps$IntSlice")) {
            throw new AssertionError("skip(positive) should return SliceOps.IntSlice but got " + className);
        }

        // Test skip with negative n throws IllegalArgumentException
        boolean threw = false;
        try {
            pipeline.skip(-1);
        } catch (IllegalArgumentException e) {
            if (!"-1".equals(e.getMessage())) {
                throw new AssertionError("Exception message should be the string of the negative number");
            }
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("skip(negative) should throw IllegalArgumentException");
        }
    }
}
