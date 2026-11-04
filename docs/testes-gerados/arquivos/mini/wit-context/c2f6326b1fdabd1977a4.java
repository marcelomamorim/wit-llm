/*
 * @test
 * @summary Test SortedOps.SizedRefSortingSink.begin(long) for boundary and exception
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.SortedOps.SizedRefSortingSink;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithTooLargeSize();
    }

    private static void testBeginWithValidSize() {
        SizedRefSortingSink<Object> sink = new SizedRefSortingSink<Object>();
        long validSize = 10L;
        sink.begin(validSize);
        // If no exception, test passes
    }

    private static void testBeginWithTooLargeSize() {
        SizedRefSortingSink<Object> sink = new SizedRefSortingSink<Object>();
        long tooLargeSize = (long) java.util.stream.Nodes.MAX_ARRAY_SIZE;
        try {
            sink.begin(tooLargeSize);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
            if (!e.getMessage().equals(java.util.stream.Nodes.BAD_SIZE)) {
                throw new RuntimeException("IllegalArgumentException thrown with unexpected message: " + e.getMessage());
            }
        }
    }
}
