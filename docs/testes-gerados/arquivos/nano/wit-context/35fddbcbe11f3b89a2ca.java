/*
 * @test
 * @summary Test for CopyOnWriteArrayList.remove() method to verify exception throwing behavior.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsUnsupportedOperationException();
    }

    public static void testRemoveThrowsUnsupportedOperationException() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        try {
            list.remove();
            System.out.println("Test failed: No exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected UnsupportedOperationException");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }
}
