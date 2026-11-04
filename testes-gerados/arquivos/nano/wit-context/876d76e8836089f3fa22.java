/*
 * @test
 * @summary Test for Collections.add(int, E) method to verify UnsupportedOperationException is thrown.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        int index = 0;
        Object element = new Object();
        try {
            Collections.add(index, (Object) element);
            System.out.println("Test failed: UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
