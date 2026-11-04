/*
 * @test
 * @summary Test SortedOps.DoubleSortingSink.begin(long) for boundary and exception cases
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.Nodes;
import java.util.stream.SpinedBuffer;

public class SortedOpsWitupTest {

    static class DoubleSortingSink extends SortedOps.DoubleSortingSink {
        // Expose begin for testing
        public DoubleSortingSink() {
            super();
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
        testBeginWithValidSize();
        testBeginWithZeroSize();
        testBeginWithMaxArraySizeThrows();
        testBeginWithGreaterThanMaxArraySizeThrows();
    }

    private static void testBeginWithValidSize() {
        DoubleSortingSink sink = new DoubleSortingSink();
        long size = 100L;
        sink.begin(size);
        SpinedBuffer.OfDouble buf = sink.getBuffer();
        if (buf == null) {
            throw new AssertionError("Buffer should not be null for positive size");
        }
        if (buf.count() != 0) {
            throw new AssertionError("Buffer should be empty after begin");
        }
    }

    private static void testBeginWithZeroSize() {
        DoubleSortingSink sink = new DoubleSortingSink();
        sink.begin(0L);
        SpinedBuffer.OfDouble buf = sink.getBuffer();
        if (buf == null) {
            throw new AssertionError("Buffer should not be null for zero size");
        }
        if (buf.count() != 0) {
            throw new AssertionError("Buffer should be empty after begin");
        }
    }

    private static void testBeginWithMaxArraySizeThrows() {
        DoubleSortingSink sink = new DoubleSortingSink();
        try {
            sink.begin(Nodes.MAX_ARRAY_SIZE);
            throw new AssertionError("Expected IllegalArgumentException for size == MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            // expected
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
        }
    }

    private static void testBeginWithGreaterThanMaxArraySizeThrows() {
        DoubleSortingSink sink = new DoubleSortingSink();
        try {
            sink.begin(((long) Nodes.MAX_ARRAY_SIZE) + 1);
            throw new AssertionError("Expected IllegalArgumentException for size > MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            // expected
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
        }
    }
}
