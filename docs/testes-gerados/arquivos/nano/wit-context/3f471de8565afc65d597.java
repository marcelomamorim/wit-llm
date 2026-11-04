/* @test    @summary Test for SortedOps.begin(long) method to verify exception on large size    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginThrowsOnLargeSize();
    }

    private static void testBeginThrowsOnLargeSize() {
        long largeSize = (long) Integer.MAX_VALUE + 1L;
        try {
            SortedOps.begin(largeSize);
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Nodes")) {
                System.err.println("Exception message does not contain expected text.");
                System.exit(1);
            }
            // Test passed
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
