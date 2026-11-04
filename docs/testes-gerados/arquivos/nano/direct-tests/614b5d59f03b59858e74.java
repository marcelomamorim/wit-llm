/* @test    @summary Test SortedOps.begin method for handling size parameter   @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithZeroSize();
        testBeginWithLargeSize();
    }

    static void testBeginWithValidSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            sortedOps.begin(10L);
            System.out.println("testBeginWithValidSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithValidSize failed: " + e);
        }
    }

    static void testBeginWithZeroSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            sortedOps.begin(0L);
            System.out.println("testBeginWithZeroSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithZeroSize failed: " + e);
        }
    }

    static void testBeginWithLargeSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            sortedOps.begin(1000000000L);
            System.out.println("testBeginWithLargeSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithLargeSize failed: " + e);
        }
    }
}
