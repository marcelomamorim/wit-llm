/*
 * @test
 * @summary Test SortedOps.begin(long) method behavior
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    // Minimal subclass to access begin method
    static class TestSortedOps<T> extends SortedOps.OfRef<T> {
        TestSortedOps() {
            super(null);
        }

        @Override
        public void begin(long size) {
            super.begin(size);
        }

        // Expose array for test
        T[] getArray() {
            return array;
        }
    }

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithMaxArraySizeThrows();
    }

    private static void testBeginWithValidSize() {
        TestSortedOps<String> op = new TestSortedOps<>();
        op.begin(10L);
        if (op.getArray() == null || op.getArray().length != 10) {
            throw new AssertionError("Array not initialized correctly for size 10");
        }
    }

    private static void testBeginWithMaxArraySizeThrows() {
        TestSortedOps<String> op = new TestSortedOps<>();
        try {
            op.begin(Nodes.MAX_ARRAY_SIZE);
            throw new AssertionError("Expected IllegalArgumentException for size >= MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
    }

    // Stub for Nodes constants as they are referenced in SortedOps
    static class Nodes {
        static final int MAX_ARRAY_SIZE = 1 << 30; // typical max array size
        static final String BAD_SIZE = "Stream size exceeds max array size";
    }
}
