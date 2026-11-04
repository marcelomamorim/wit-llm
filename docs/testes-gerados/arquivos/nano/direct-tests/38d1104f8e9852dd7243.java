/* @test    @summary Test SortedOps.begin method for handling size parameter   @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;
import java.util.stream.SpinedBuffer;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithZeroSize();
        testBeginWithLargeSize();
    }

    static void testBeginWithValidSize() {
        SortedOps sortedOps = new SortedOps();
        long size = 10;
        try {
            sortedOps.begin(size);
            System.out.println("testBeginWithValidSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithValidSize failed: " + e);
        }
    }

    static void testBeginWithZeroSize() {
        SortedOps sortedOps = new SortedOps();
        long size = 0;
        try {
            sortedOps.begin(size);
            System.out.println("testBeginWithZeroSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithZeroSize failed: " + e);
        }
    }

    static void testBeginWithLargeSize() {
        SortedOps sortedOps = new SortedOps();
        long size = Nodes.MAX_ARRAY_SIZE + 1;
        try {
            sortedOps.begin(size);
            System.err.println("testBeginWithLargeSize failed: exception expected");
        } catch (IllegalArgumentException e) {
            System.out.println("testBeginWithLargeSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithLargeSize failed: " + e);
        }
    }
}
