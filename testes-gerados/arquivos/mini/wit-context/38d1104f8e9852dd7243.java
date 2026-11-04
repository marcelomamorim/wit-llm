/*
 * @test
 * @summary Test java.util.stream.SortedOps.IntSortingSink.begin(long) for boundary and exception behavior
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.Nodes;
import java.util.stream.SpinedBuffer;

public class SortedOpsWitupTest {

    // Minimal stub subclass to access begin(long) method
    static class IntSortingSink extends SortedOps.IntSortingSink {
        IntSortingSink() {
            super();
        }
        // Expose begin for testing
        @Override
        public void begin(long size) {
            super.begin(size);
        }
    }

    public static void main(String[] args) {
        IntSortingSink sink = new IntSortingSink();

        // Test normal behavior: size = 0, expect no exception and buffer initialized empty
        sink.begin(0L);
        if (sink.b == null) {
            throw new AssertionError("Buffer should be initialized for size 0");
        }

        // Test normal behavior: size = 10, expect buffer initialized with capacity 10
        sink.begin(10L);
        if (sink.b == null) {
            throw new AssertionError("Buffer should be initialized for positive size");
        }
        if (sink.b.count() != 0) {
            throw new AssertionError("Buffer should be empty after begin");
        }

        // Test exception behavior: size >= Nodes.MAX_ARRAY_SIZE, expect IllegalArgumentException
        long badSize = Nodes.MAX_ARRAY_SIZE;
        boolean threw = false;
        try {
            sink.begin(badSize);
        } catch (IllegalArgumentException e) {
            threw = true;
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for size >= Nodes.MAX_ARRAY_SIZE");
        }

        System.out.println("All tests passed.");
    }
}
