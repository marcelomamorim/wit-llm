/*
 * @test
 * @summary Test for CopyOnWriteArrayList.add(E e) method.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testAddThrowsUnsupportedOperationException();
    }

    public static void testAddThrowsUnsupportedOperationException() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        try {
            list.add(1);
            System.out.println("Test Failed: No exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test Passed: Caught UnsupportedOperationException as expected");
        } catch (Throwable t) {
            System.out.println("Test Failed: Caught unexpected exception: " + t);
        }
    }
}
