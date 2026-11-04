/* @test    @summary Test for SortedOps.begin(long size) method    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithZeroSize();
        testBeginWithLargeSize();
        testBeginWithNegativeSize();
    }

    static void testBeginWithValidSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Using a small valid size
            sortedOps.begin(10);
            System.out.println("testBeginWithValidSize passed");
        } catch (Exception e) {
            System.out.println("testBeginWithValidSize failed: " + e);
        }
    }

    static void testBeginWithZeroSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Using zero size
            sortedOps.begin(0);
            System.out.println("testBeginWithZeroSize passed");
        } catch (Exception e) {
            System.out.println("testBeginWithZeroSize failed: " + e);
        }
    }

    static void testBeginWithLargeSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Using a size larger than Nodes.MAX_ARRAY_SIZE to trigger exception
            long largeSize = 1L + Integer.MAX_VALUE;
            sortedOps.begin(largeSize);
            System.out.println("testBeginWithLargeSize failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testBeginWithLargeSize passed");
        } catch (Exception e) {
            System.out.println("testBeginWithLargeSize failed: " + e);
        }
    }

    static void testBeginWithNegativeSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Using a negative size
            sortedOps.begin(-5);
            System.out.println("testBeginWithNegativeSize passed");
        } catch (Exception e) {
            System.out.println("testBeginWithNegativeSize failed: " + e);
        }
    }
}
