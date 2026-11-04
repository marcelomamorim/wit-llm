/*
 * @test
 * @summary Test SortedOps.begin(long) method behavior
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.Nodes;
import java.util.stream.SpinedBuffer;

public class SortedOpsWitupTest {

    // Minimal subclass to expose begin(long) for testing
    static class TestSortedOps extends SortedOps.OfDouble {
        public TestSortedOps() {
            super(null);
        }
        @Override
        public void begin(long size) {
            super.begin(size);
        }
        public SpinedBuffer.OfDouble getBuffer() {
            return b;
        }
    }

    public static void main(String[] args) {
        testBeginWithZeroSize();
        testBeginWithPositiveSize();
        testBeginWithMaxArraySizeThrows();
        testBeginWithGreaterThanMaxArraySizeThrows();
    }

    private static void testBeginWithZeroSize() {
        TestSortedOps op = new TestSortedOps();
        op.begin(0L);
        SpinedBuffer.OfDouble buf = op.getBuffer();
        if (buf == null) {
            throw new AssertionError("Buffer should not be null for size 0");
        }
        if (buf.count() != 0) {
            throw new AssertionError("Buffer count should be 0 for size 0");
        }
    }

    private static void testBeginWithPositiveSize() {
        TestSortedOps op = new TestSortedOps();
        int size = 10;
        op.begin(size);
        SpinedBuffer.OfDouble buf = op.getBuffer();
        if (buf == null) {
            throw new AssertionError("Buffer should not be null for positive size");
        }
        if (buf.count() != 0) {
            throw new AssertionError("Buffer count should be 0 after begin");
        }
    }

    private static void testBeginWithMaxArraySizeThrows() {
        TestSortedOps op = new TestSortedOps();
        try {
            op.begin(Nodes.MAX_ARRAY_SIZE);
            throw new AssertionError("Expected IllegalArgumentException for size == MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
    }

    private static void testBeginWithGreaterThanMaxArraySizeThrows() {
        TestSortedOps op = new TestSortedOps();
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
