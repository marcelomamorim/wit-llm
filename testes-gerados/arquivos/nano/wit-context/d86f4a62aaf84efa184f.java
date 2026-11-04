/* @test    @summary Test for SortedOps.begin method handling large size input    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithLargeSize();
        testBeginWithNormalSize();
    }

    private static void testBeginWithLargeSize() {
        long largeSize = (long) Integer.MAX_VALUE + 1L;
        try {
            SortedOps.begin(largeSize);
            System.out.println("FAIL: Expected IllegalArgumentException for large size");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for large size");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testBeginWithNormalSize() {
        long normalSize = 10L;
        try {
            SortedOps.begin(normalSize);
            System.out.println("PASS: begin() executed without exception for normal size");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for normal size: " + t);
        }
    }
}
