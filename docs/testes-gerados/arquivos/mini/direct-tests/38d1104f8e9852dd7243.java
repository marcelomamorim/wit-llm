/*
 * @test
 * @summary Test SortedOps.begin(long) method behavior
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.Nodes;
import java.util.stream.SpinedBuffer;

public class SortedOpsWitupTest {

    // Minimal subclass to expose begin method for testing
    static class TestSortedOps extends SortedOps.OfInt {
        TestSortedOps() {
            super(null);
        }
        @Override
        public void begin(long size) {
            super.begin(size);
        }
        SpinedBuffer.OfInt getBuffer() {
            return b;
        }
    }

    public static void main(String[] args) {
        TestSortedOps op = new TestSortedOps();

        // Test with size 0: buffer should be empty SpinedBuffer.OfInt
        op.begin(0L);
        if (op.getBuffer() == null) {
            throw new AssertionError("Buffer should not be null for size 0");
        }

        // Test with positive size less than MAX_ARRAY_SIZE
        int testSize = 100;
        op.begin(testSize);
        SpinedBuffer.OfInt buf = op.getBuffer();
        if (buf == null) {
            throw new AssertionError("Buffer should not be null for positive size");
        }

        // Test with size exactly MAX_ARRAY_SIZE - 1 (valid)
        long maxValidSize = Nodes.MAX_ARRAY_SIZE - 1L;
        op.begin(maxValidSize);
        if (op.getBuffer() == null) {
            throw new AssertionError("Buffer should not be null for size just below MAX_ARRAY_SIZE");
        }

        // Test with size equal to MAX_ARRAY_SIZE (should throw IllegalArgumentException)
        try {
            op.begin(Nodes.MAX_ARRAY_SIZE);
            throw new AssertionError("Expected IllegalArgumentException for size >= MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }

        // Test with size greater than MAX_ARRAY_SIZE (should throw IllegalArgumentException)
        try {
            op.begin(Nodes.MAX_ARRAY_SIZE + 1L);
            throw new AssertionError("Expected IllegalArgumentException for size > MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
    }
}
