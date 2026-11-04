/*
 * @test
 * @summary Test for Collections.remove() method to observe exception behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveMethodThrowsException();
    }

    public static void testRemoveMethodThrowsException() {
        try {
            Collections.remove();
            System.out.println("Test failed: No exception thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }
}
