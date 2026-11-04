/*
 * @test
 * @summary Test SortedOps.RefSortingSink.begin(long) for boundary and error conditions
 * @run main SortedOpsWitupTest
 */

import java.util.ArrayList;
import java.util.stream.SortedOps;
import java.util.stream.SortedOps.RefSortingSink;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithNegativeSize();
        testBeginWithTooLargeSize();
    }

    private static void testBeginWithValidSize() {
        RefSortingSink<Object> sink = new RefSortingSink<Object>();
        // Use a valid size (e.g., 10)
        sink.begin(10L);
        // Expect list to be non-null and size 0 initially
        if (sink.list == null) {
            throw new AssertionError("list should be initialized for valid size");
        }
        if (sink.list.size() != 0) {
            throw new AssertionError("list should be empty after begin");
        }
    }

    private static void testBeginWithNegativeSize() {
        RefSortingSink<Object> sink = new RefSortingSink<Object>();
        // Negative size should create empty list
        sink.begin(-1L);
        if (sink.list == null) {
            throw new AssertionError("list should be initialized even for negative size");
        }
        if (sink.list.size() != 0) {
            throw new AssertionError("list should be empty after begin with negative size");
        }
    }

    private static void testBeginWithTooLargeSize() {
        RefSortingSink<Object> sink = new RefSortingSink<Object>();
        try {
            sink.begin((long) (java.util.stream.Nodes.MAX_ARRAY_SIZE));
            throw new AssertionError("Expected IllegalArgumentException for size >= MAX_ARRAY_SIZE");
        } catch (IllegalArgumentException e) {
            // Expected exception
            if (!java.util.stream.Nodes.BAD_SIZE.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
    }
}
