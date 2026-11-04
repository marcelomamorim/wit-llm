/* @test    @summary Test for SortedOps.begin(long) method to verify exception handling for large sizes    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithLargeSize();
        testBeginWithZeroSize();
        testBeginWithNegativeSize();
    }

    private static void testBeginWithLargeSize() {
        try {
            SortedOps.begin(Long.MAX_VALUE);
            System.out.println("FAIL: Expected IllegalArgumentException for large size");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for large size");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testBeginWithZeroSize() {
        try {
            SortedOps.begin(0L);
            System.out.println("PASS: No exception for zero size");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for zero size: " + t);
        }
    }

    private static void testBeginWithNegativeSize() {
        try {
            SortedOps.begin(-1L);
            System.out.println("PASS: No exception for negative size");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for negative size: " + t);
        }
    }
}
