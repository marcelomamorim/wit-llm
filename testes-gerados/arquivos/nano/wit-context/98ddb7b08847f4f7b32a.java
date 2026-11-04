/*
 * @test
 * @summary Test for Collections.remove() method to verify UnsupportedOperationException is thrown.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        try {
            Collections.remove();
            System.err.println("Test failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException was correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
