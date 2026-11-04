/*
 * @test
 * @summary Test for CopyOnWriteArrayList.remove() method to verify it throws UnsupportedOperationException.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        try {
            list.remove();
            System.out.println("Test failed: no exception thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected.");
        } catch (Exception e) {
            System.out.println("Test failed: unexpected exception: " + e);
        }
    }
}
