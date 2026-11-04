/*
 * @test
 * @summary Test for LinkedBlockingDeque.remove() method behavior.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullLastRet();
        testRemoveWithNonNullLastRet();
    }

    private static void testRemoveWithNullLastRet() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        // Simulate internal state: lastRet == null
        // Since lastRet is private, we test behavior by calling remove() when no element is set
        try {
            deque.remove();
            // Expect an IllegalStateException as per source code
            System.out.println("FAIL: Expected IllegalStateException when lastRet == null");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testRemoveWithNonNullLastRet() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        // To set lastRet, we need to insert an element and simulate internal state
        // Since lastRet is private, we simulate by adding an element and calling remove() after setting lastRet
        // But as lastRet is private, we cannot set it directly; instead, we test the behavior when lastRet != null
        // We can do this by calling remove() after adding an element, which internally sets lastRet
        deque.add(new Object());
        // Now, calling remove() should not throw IllegalStateException
        try {
            deque.remove();
            System.out.println("PASS: remove() executed successfully with non-null lastRet");
        } catch (IllegalStateException e) {
            System.out.println("FAIL: Unexpected IllegalStateException: " + e);
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
