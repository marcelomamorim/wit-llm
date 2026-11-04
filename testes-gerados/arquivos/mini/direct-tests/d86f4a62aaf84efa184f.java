/*
 * @test
 * @summary Test SortedOps.begin(long) method behavior
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    // Minimal subclass to access begin(long) method
    static class TestOfDouble extends SortedOps.OfDouble {
        TestOfDouble() {
            super(null);
        }
        @Override
        public void begin(long size) {
            super.begin(size);
        }
        double[] getArray() {
            return array;
        }
    }

    public static void main(String[] args) {
        TestOfDouble op = new TestOfDouble();

        // Test with size 0: array length should be 0
        op.begin(0L);
        if (op.getArray() == null || op.getArray().length != 0) {
            throw new AssertionError("Expected array length 0 for size 0");
        }

        // Test with small positive size
        op.begin(10L);
        if (op.getArray() == null || op.getArray().length != 10) {
            throw new AssertionError("Expected array length 10 for size 10");
        }

        // Test with size just below MAX_ARRAY_SIZE
        long maxSize = java.util.stream.Nodes.MAX_ARRAY_SIZE - 1L;
        op.begin(maxSize);
        if (op.getArray() == null || op.getArray().length != (int) maxSize) {
            throw new AssertionError("Expected array length " + maxSize + " for size " + maxSize);
        }

        // Test with size equal to MAX_ARRAY_SIZE: expect IllegalArgumentException
        boolean threw = false;
        try {
            op.begin(java.util.stream.Nodes.MAX_ARRAY_SIZE);
        } catch (IllegalArgumentException e) {
            if (!java.util.stream.Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for size >= MAX_ARRAY_SIZE");
        }

        // Test with size greater than MAX_ARRAY_SIZE: expect IllegalArgumentException
        threw = false;
        try {
            op.begin(java.util.stream.Nodes.MAX_ARRAY_SIZE + 1L);
        } catch (IllegalArgumentException e) {
            if (!java.util.stream.Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for size > MAX_ARRAY_SIZE");
        }
    }
}
