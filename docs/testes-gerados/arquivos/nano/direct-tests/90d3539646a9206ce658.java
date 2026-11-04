/* @test    @summary Test for SortedOps.begin(long size) method behavior.    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithLargeSize();
        testBeginWithNegativeSize();
    }

    private static void testBeginWithValidSize() {
        SortedOps instance = new SortedOps();
        try {
            instance.begin(10L);
            System.out.println("testBeginWithValidSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithValidSize failed: " + e);
        }
    }

    private static void testBeginWithLargeSize() {
        SortedOps instance = new SortedOps();
        try {
            instance.begin(Long.MAX_VALUE);
            System.err.println("testBeginWithLargeSize failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testBeginWithLargeSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithLargeSize failed: " + e);
        }
    }

    private static void testBeginWithNegativeSize() {
        SortedOps instance = new SortedOps();
        try {
            instance.begin(-1L);
            System.out.println("testBeginWithNegativeSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithNegativeSize failed: " + e);
        }
    }
}
