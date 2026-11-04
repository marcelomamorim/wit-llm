/*
 * @test
 * @summary Test SortedOps.SizedIntSortingSink.begin(long) for boundary and exception
 * @run main SortedOpsWitupTest
 */

import java.util.stream.SortedOps;
import java.util.stream.SortedOps.SizedIntSortingSink;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithTooLargeSize();
    }

    private static void testBeginWithValidSize() {
        SizedIntSortingSink sink = new SizedIntSortingSink();
        long validSize = 10L;
        sink.begin(validSize);
        // If no exception, test passes for valid size
    }

    private static void testBeginWithTooLargeSize() {
        SizedIntSortingSink sink = new SizedIntSortingSink();
        long tooLargeSize = (long) java.util.stream.Nodes.MAX_ARRAY_SIZE;
        boolean threw = false;
        try {
            sink.begin(tooLargeSize);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for size >= Nodes.MAX_ARRAY_SIZE");
        }
    }
}
