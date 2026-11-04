/* @test    @summary Test for SortedOps.begin(long size) method in java.util.stream package    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithMaxArraySize();
        testBeginWithInvalidSize();
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

    static void testBeginWithMaxArraySize() {
        SortedOps sortedOps = new SortedOps();
        long size = 9223372036854775807L; // Long.MAX_VALUE
        try {
            sortedOps.begin(size);
            System.err.println("testBeginWithMaxArraySize failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testBeginWithMaxArraySize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithMaxArraySize failed: " + e);
        }
    }

    static void testBeginWithInvalidSize() {
        SortedOps sortedOps = new SortedOps();
        long size = -1;
        try {
            sortedOps.begin(size);
            System.err.println("testBeginWithInvalidSize failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testBeginWithInvalidSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithInvalidSize failed: " + e);
        }
    }
}
