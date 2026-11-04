/* @test    @summary Test for SortedOps.begin(long) method behavior with valid and invalid sizes    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithExceedingSize();
    }

    private static void testBeginWithValidSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Valid size less than Nodes.MAX_ARRAY_SIZE
            sortedOps.begin(10L);
            System.out.println("testBeginWithValidSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithValidSize failed: " + e);
        }
    }

    private static void testBeginWithExceedingSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Size equal or greater than Nodes.MAX_ARRAY_SIZE should throw IllegalArgumentException
            sortedOps.begin(1000000000L); // assuming Nodes.MAX_ARRAY_SIZE is less than this
            System.err.println("testBeginWithExceedingSize failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Nodes.BAD_SIZE")) {
                System.out.println("testBeginWithExceedingSize passed");
            } else {
                System.err.println("testBeginWithExceedingSize failed: wrong exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("testBeginWithExceedingSize failed: " + e);
        }
    }
}
