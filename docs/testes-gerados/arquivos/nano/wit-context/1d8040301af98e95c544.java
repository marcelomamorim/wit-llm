/*
 * @test
 * @summary Tests for ConcurrentLinkedDeque.remove()
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveWithLastRetNull();
        testRemoveWithValidNode();
    }

    private static void testRemoveWithLastRetNull() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        // Simulate internal state: lastRet == null
        // Since lastRet is private, we test behavior when remove() is called without prior iteration
        try {
            deque.remove();
            // Expect IllegalStateException as per source code
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testRemoveWithValidNode() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("element");
        // Access internal node via reflection or simulate scenario where lastRet points to a node
        // Since internal node is private, we simulate by calling iterator and remove
        java.util.Iterator<String> it = deque.iterator();
        if (it.hasNext()) {
            it.next();
            try {
                it.remove();
                System.out.println("PASS: remove() executed without exception");
            } catch (Exception e) {
                System.out.println("FAIL: Unexpected exception during remove(): " + e);
            }
        } else {
            System.out.println("FAIL: Iterator has no elements to remove");
        }
    }
}
