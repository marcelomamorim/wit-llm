/* @test    @summary Test that Collections.remove() throws UnsupportedOperationException    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        try {
            Collections.remove();
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Passed: Collections.remove() threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
