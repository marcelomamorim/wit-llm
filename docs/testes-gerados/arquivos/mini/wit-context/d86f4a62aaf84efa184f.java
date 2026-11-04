/*
 * @test
 * @summary Test java.util.stream.SortedOps.SizedDoubleSortingSink.begin(long) for boundary and exception
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.SortedOps.SizedDoubleSortingSink;

public class SortedOpsWitupTest {
    public static void main(String[] args) {
        testBeginThrowsOnTooLargeSize();
        testBeginAllocatesArrayForValidSize();
    }

    private static void testBeginThrowsOnTooLargeSize() {
        SizedDoubleSortingSink sink = new SizedDoubleSortingSink();
        long tooLargeSize = (long) Integer.MAX_VALUE + 1L; // Nodes.MAX_ARRAY_SIZE is Integer.MAX_VALUE + 8, so use a value >= that
        // Using Nodes.MAX_ARRAY_SIZE from source is not possible here, so use a value >= Integer.MAX_VALUE + 8
        // to trigger exception as per method contract.
        try {
            sink.begin(tooLargeSize);
            throw new AssertionError("Expected IllegalArgumentException for size >= Nodes.MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException iae) {
            // expected
            String msg = iae.getMessage();
            if (msg == null || msg.isEmpty()) {
                throw new AssertionError("IllegalArgumentException message is empty");
            }
        }
    }

    private static void testBeginAllocatesArrayForValidSize() {
        SizedDoubleSortingSink sink = new SizedDoubleSortingSink();
        int validSize = 10;
        sink.begin(validSize);
        if (sink.array == null) {
            throw new AssertionError("Array not allocated for valid size");
        }
        if (sink.array.length != validSize) {
            throw new AssertionError("Array length " + sink.array.length + " != expected " + validSize);
        }
    }
}
