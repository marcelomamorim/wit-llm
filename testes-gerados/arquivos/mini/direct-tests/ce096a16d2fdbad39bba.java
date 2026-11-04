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
    static class TestSortedOps extends SortedOps.OfLong {
        public TestSortedOps() {
            super(null);
        }

        @Override
        public void begin(long size) {
            super.begin(size);
        }

        public SpinedBuffer.OfLong getBuffer() {
            return b;
        }
    }

    public static void main(String[] args) {
        testBeginWithZeroSize();
        testBeginWithPositiveSize();
        testBeginWithMaxArraySizeMinusOne();
        testBeginWithMaxArraySizeThrows();
    }

    private static void testBeginWithZeroSize() {
        TestSortedOps op = new TestSortedOps();
        op.begin(0L);
        SpinedBuffer.OfLong buf = op.getBuffer();
        if (buf == null) {
            throw new RuntimeException("Buffer should not be null for size 0");
        }
        if (buf.count() != 0) {
            throw new RuntimeException("Buffer count should be 0 for size 0");
        }
    }

    private static void testBeginWithPositiveSize() {
        TestSortedOps op = new TestSortedOps();
        int size = 10;
        op.begin(size);
        SpinedBuffer.OfLong buf = op.getBuffer();
        if (buf == null) {
            throw new RuntimeException("Buffer should not be null for positive size");
        }
        if (buf.count() != 0) {
            throw new RuntimeException("Buffer count should be 0 after begin");
        }
    }

    private static void testBeginWithMaxArraySizeMinusOne() {
        TestSortedOps op = new TestSortedOps();
        long size = Nodes.MAX_ARRAY_SIZE - 1L;
        op.begin(size);
        SpinedBuffer.OfLong buf = op.getBuffer();
        if (buf == null) {
            throw new RuntimeException("Buffer should not be null for size just below MAX_ARRAY_SIZE");
        }
    }

    private static void testBeginWithMaxArraySizeThrows() {
        TestSortedOps op = new TestSortedOps();
        long size = Nodes.MAX_ARRAY_SIZE;
        try {
            op.begin(size);
            throw new RuntimeException("Expected IllegalArgumentException for size >= MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new RuntimeException("Exception message mismatch", e);
            }
        }
    }
}
