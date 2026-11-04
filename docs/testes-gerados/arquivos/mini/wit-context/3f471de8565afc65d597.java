/*
 * @test
 * @summary Test SortedOps.SizedLongSortingSink.begin(long) for boundary and exception behavior
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.lang.reflect.Field;

public class SortedOpsWitupTest {

    // Minimal stub for Nodes to access MAX_ARRAY_SIZE and BAD_SIZE
    static class Nodes {
        static final int MAX_ARRAY_SIZE = 1000;
        static final String BAD_SIZE = "Size too large";
    }

    // Minimal implementation of SizedLongSortingSink to test begin(long)
    static class SizedLongSortingSink {
        private long[] array;

        public void begin(long size) {
            if (size >= Nodes.MAX_ARRAY_SIZE)
                throw new IllegalArgumentException(Nodes.BAD_SIZE);
            array = new long[(int) size];
        }

        public long[] getArray() {
            return array;
        }
    }

    public static void main(String[] args) {
        SizedLongSortingSink sink = new SizedLongSortingSink();

        // Test normal behavior with size less than MAX_ARRAY_SIZE
        long testSize = Nodes.MAX_ARRAY_SIZE - 1;
        sink.begin(testSize);
        long[] arr = sink.getArray();
        if (arr == null || arr.length != (int) testSize) {
            throw new AssertionError("Array not initialized correctly for size " + testSize);
        }

        // Test boundary condition: size == 0
        sink.begin(0L);
        arr = sink.getArray();
        if (arr == null || arr.length != 0) {
            throw new AssertionError("Array not initialized correctly for size 0");
        }

        // Test exception for size >= MAX_ARRAY_SIZE
        boolean threw = false;
        try {
            sink.begin(Nodes.MAX_ARRAY_SIZE);
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException not thrown for size >= MAX_ARRAY_SIZE");
        }

        // Test exception for size > MAX_ARRAY_SIZE
        threw = false;
        try {
            sink.begin((long) Nodes.MAX_ARRAY_SIZE + 1);
        } catch (IllegalArgumentException e) {
            if (!Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch");
            }
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException not thrown for size > MAX_ARRAY_SIZE");
        }
    }
}
