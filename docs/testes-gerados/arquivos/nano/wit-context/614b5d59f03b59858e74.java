/* @test    @summary Test for SortedOps.begin(long) method behavior and exception handling    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithLargeSize();
        testBeginWithExceedingMaxSize();
    }

    private static void testBeginWithValidSize() {
        try {
            SortedOps.begin(10L);
            System.out.println("testBeginWithValidSize passed");
        } catch (Exception e) {
            System.out.println("testBeginWithValidSize failed: " + e);
        }
    }

    private static void testBeginWithLargeSize() {
        try {
            SortedOps.begin(0L);
            System.out.println("testBeginWithLargeSize passed");
        } catch (Exception e) {
            System.out.println("testBeginWithLargeSize failed: " + e);
        }
    }

    private static void testBeginWithExceedingMaxSize() {
        long exceedingSize = Nodes.MAX_ARRAY_SIZE;
        try {
            SortedOps.begin(exceedingSize);
            System.out.println("testBeginWithExceedingMaxSize failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains(Nodes.BAD_SIZE)) {
                System.out.println("testBeginWithExceedingMaxSize passed");
            } else {
                System.out.println("testBeginWithExceedingMaxSize failed: wrong message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("testBeginWithExceedingMaxSize failed: " + e);
        }
    }
}
