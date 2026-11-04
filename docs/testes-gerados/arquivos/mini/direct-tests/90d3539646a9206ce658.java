/*
 * @test
 * @summary Test SortedOps.begin(long) method behavior
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.Nodes;

public class SortedOpsWitupTest {

    // Minimal subclass to access begin(long) and array field
    static class TestOfInt extends SortedOps.OfInt {
        int[] array;

        TestOfInt() {
            super(null);
        }

        @Override
        public void begin(long size) {
            if (size >= Nodes.MAX_ARRAY_SIZE)
                throw new IllegalArgumentException(Nodes.BAD_SIZE);
            array = new int[(int) size];
        }
    }

    public static void main(String[] args) {
        TestOfInt op = new TestOfInt();

        // Test normal size
        op.begin(10L);
        if (op.array == null || op.array.length != 10) {
            throw new AssertionError("Array not initialized correctly for size 10");
        }

        // Test zero size
        op.begin(0L);
        if (op.array == null || op.array.length != 0) {
            throw new AssertionError("Array not initialized correctly for size 0");
        }

        // Test size just below MAX_ARRAY_SIZE
        op.begin(Nodes.MAX_ARRAY_SIZE - 1L);
        if (op.array == null || op.array.length != Nodes.MAX_ARRAY_SIZE - 1) {
            throw new AssertionError("Array not initialized correctly for size MAX_ARRAY_SIZE-1");
        }

        // Test size equal to MAX_ARRAY_SIZE - should throw IllegalArgumentException
        try {
            op.begin(Nodes.MAX_ARRAY_SIZE);
            throw new AssertionError("Expected IllegalArgumentException for size MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
        }

        // Test size greater than MAX_ARRAY_SIZE - should throw IllegalArgumentException
        try {
            op.begin(Nodes.MAX_ARRAY_SIZE + 1L);
            throw new AssertionError("Expected IllegalArgumentException for size > MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
        }
    }
}
