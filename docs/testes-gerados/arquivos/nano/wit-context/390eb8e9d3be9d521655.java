/*
 * @test
 * @summary Test for CopyOnWriteArrayList.add method to verify exception throwing behavior.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testAddThrowsUnsupportedOperationException();
    }

    public static void testAddThrowsUnsupportedOperationException() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        try {
            list.add(new Object());
            System.out.println("Test failed: No exception thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
