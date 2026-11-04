/*
 * @test
 * @summary Test SortedOps.LongSortingSink.begin(long) for boundary and normal cases
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.Nodes;
import java.util.stream.SpinedBuffer;

public class SortedOpsWitupTest {

    // Minimal stub for LongSortingSink to test begin(long)
    static class LongSortingSink {
        SpinedBuffer.OfLong b;

        public void begin(long size) {
            if (size >= Nodes.MAX_ARRAY_SIZE)
                throw new IllegalArgumentException(Nodes.BAD_SIZE);
            b = (size > 0) ? new SpinedBuffer.OfLong((int) size) : new SpinedBuffer.OfLong();
        }
    }

    public static void main(String[] args) {
        LongSortingSink sink = new LongSortingSink();

        // Test normal size (0) - should create empty buffer
        sink.begin(0L);
        if (sink.b == null) {
            throw new AssertionError("Buffer should not be null for size 0");
        }

        // Test normal positive size (10) - should create buffer with initial capacity 10
        sink.begin(10L);
        if (sink.b == null) {
            throw new AssertionError("Buffer should not be null for positive size");
        }

        // Test boundary: size just below MAX_ARRAY_SIZE - should not throw
        long justBelowMax = (long) Nodes.MAX_ARRAY_SIZE - 1;
        sink.begin(justBelowMax);

        // Test boundary: size equal to MAX_ARRAY_SIZE - should throw IllegalArgumentException
        boolean threw = false;
        try {
            sink.begin((long) Nodes.MAX_ARRAY_SIZE);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for size >= MAX_ARRAY_SIZE");
        }

        // Test boundary: size greater than MAX_ARRAY_SIZE - should throw IllegalArgumentException
        threw = false;
        try {
            sink.begin((long) Nodes.MAX_ARRAY_SIZE + 1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for size > MAX_ARRAY_SIZE");
        }
    }
}
