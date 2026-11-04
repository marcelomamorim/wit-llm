/*
 * @test
 * @summary Test SortedOps.begin(long) method behavior
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    // Minimal subclass to expose begin method for testing
    static class TestSortedOps extends SortedOps.OfLong {
        TestSortedOps() {
            super(null);
        }
        @Override
        public void begin(long size) {
            super.begin(size);
        }
        long[] getArray() {
            return array;
        }
    }

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithMaxArraySize();
        testBeginWithTooLargeSize();
    }

    private static void testBeginWithValidSize() {
        TestSortedOps op = new TestSortedOps();
        long size = 10L;
        op.begin(size);
        long[] arr = op.getArray();
        if (arr == null || arr.length != (int) size) {
            throw new AssertionError("Array not initialized correctly for size " + size);
        }
    }

    private static void testBeginWithMaxArraySize() {
        TestSortedOps op = new TestSortedOps();
        try {
            op.begin(java.util.stream.Nodes.MAX_ARRAY_SIZE);
            throw new AssertionError("Expected IllegalArgumentException for size MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!java.util.stream.Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
    }

    private static void testBeginWithTooLargeSize() {
        TestSortedOps op = new TestSortedOps();
        try {
            op.begin((long) java.util.stream.Nodes.MAX_ARRAY_SIZE + 1);
            throw new AssertionError("Expected IllegalArgumentException for size > MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!java.util.stream.Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
    }
}
