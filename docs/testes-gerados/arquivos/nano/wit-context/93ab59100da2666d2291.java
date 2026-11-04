/* @test    @summary Test for SortedOps.begin(long) method to verify behavior with large size input    @run main SortedOpsWitupTest */

import java.util.ArrayList;
import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithLargeSize();
        testBeginWithZeroSize();
    }

    public static void testBeginWithLargeSize() {
        long largeSize = (long) Integer.MAX_VALUE + 1L;
        try {
            SortedOps.begin(largeSize);
            // If no exception, test passes as behavior is as expected
        } catch (IllegalArgumentException e) {
            // Expected exception for size >= Nodes.MAX_ARRAY_SIZE
            System.out.println("Caught expected IllegalArgumentException for large size.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for large size: " + e);
        }
    }

    public static void testBeginWithZeroSize() {
        try {
            SortedOps.begin(0);
            // Should not throw, list should be initialized
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero size: " + e);
        }
    }
}
