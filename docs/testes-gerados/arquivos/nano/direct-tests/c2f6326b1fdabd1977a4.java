/* @test    @summary Test SortedOps.begin with valid and invalid sizes    @run main SortedOpsWitupTest */

import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithExcessiveSize();
    }

    static void testBeginWithValidSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Use a reasonable size less than Nodes.MAX_ARRAY_SIZE
            sortedOps.begin(10);
            System.out.println("testBeginWithValidSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithValidSize failed: " + e);
        }
    }

    static void testBeginWithExcessiveSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Use a size equal or greater than Nodes.MAX_ARRAY_SIZE to trigger exception
            long excessiveSize = (long) Integer.MAX_VALUE + 1;
            sortedOps.begin(excessiveSize);
            System.err.println("testBeginWithExcessiveSize failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testBeginWithExcessiveSize passed");
        } catch (Exception e) {
            System.err.println("testBeginWithExcessiveSize failed: " + e);
        }
    }
}
