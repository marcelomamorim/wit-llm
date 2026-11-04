/*
 * @test
 * @summary Test for CopyOnWriteArrayList.set method to verify exception throwing behavior.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testSetThrowsUnsupportedOperationException();
    }

    public static void testSetThrowsUnsupportedOperationException() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("element");
        try {
            list.set(0, "newElement");
            System.out.println("Test failed: No exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected UnsupportedOperationException");
        } catch (Exception e) {
            System.out.println("Test failed: Caught unexpected exception: " + e);
        }
    }
}
