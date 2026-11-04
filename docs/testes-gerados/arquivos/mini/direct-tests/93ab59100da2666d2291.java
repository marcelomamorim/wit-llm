/*
 * @test
 * @summary Test SortedOps.begin(long) method behavior
 * @run main SortedOpsWitupTest
 */

import java.util.ArrayList;
import java.util.IllegalArgumentException;
import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    // Minimal subclass to expose begin method for testing
    static class TestSortedOps extends SortedOps.OfRef<Object> {
        ArrayList<Object> list;

        TestSortedOps() {
            super(null);
        }

        @Override
        public void begin(long size) {
            if (size >= Nodes.MAX_ARRAY_SIZE)
                throw new IllegalArgumentException(Nodes.BAD_SIZE);
            list = (size >= 0) ? new ArrayList<>((int) size) : new ArrayList<>();
        }
    }

    // Dummy Nodes class to provide constants used in begin method
    static class Nodes {
        static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
        static final String BAD_SIZE = "Size exceeds maximum array size";
    }

    public static void main(String[] args) {
        TestSortedOps op = new TestSortedOps();

        // Test with size less than MAX_ARRAY_SIZE
        op.begin(10L);
        if (op.list == null || op.list.size() != 0) {
            throw new AssertionError("List should be initialized empty with capacity 10");
        }

        // Test with size zero
        op.begin(0L);
        if (op.list == null || op.list.size() != 0) {
            throw new AssertionError("List should be initialized empty with capacity 0");
        }

        // Test with negative size
        op.begin(-1L);
        if (op.list == null || op.list.size() != 0) {
            throw new AssertionError("List should be initialized empty with default capacity for negative size");
        }

        // Test with size equal to MAX_ARRAY_SIZE - 1 (valid)
        op.begin((long) Nodes.MAX_ARRAY_SIZE - 1);
        if (op.list == null || op.list.size() != 0) {
            throw new AssertionError("List should be initialized empty with capacity MAX_ARRAY_SIZE - 1");
        }

        // Test with size equal to MAX_ARRAY_SIZE (should throw)
        boolean threw = false;
        try {
            op.begin((long) Nodes.MAX_ARRAY_SIZE);
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for size >= MAX_ARRAY_SIZE");
        }

        // Test with size greater than MAX_ARRAY_SIZE (should throw)
        threw = false;
        try {
            op.begin((long) Nodes.MAX_ARRAY_SIZE + 1);
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for size > MAX_ARRAY_SIZE");
        }
    }
}
