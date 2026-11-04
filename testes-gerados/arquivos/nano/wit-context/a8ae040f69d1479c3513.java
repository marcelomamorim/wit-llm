/*
 * @test
 * @summary Test for java.util.Collections.remove() method.
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
            System.out.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
