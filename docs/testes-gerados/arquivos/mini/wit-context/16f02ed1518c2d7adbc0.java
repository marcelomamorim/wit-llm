/*
 * @test
 * @summary Test LinkedBlockingDeque.AbstractItr.remove() exception paths
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    // Minimal subclass to expose AbstractItr and test remove()
    static class TestItr extends LinkedBlockingDeque<Integer>.AbstractItr {
        TestItr(LinkedBlockingDeque<Integer> deque) {
            deque.super();
        }
        // Expose remove() publicly for testing
        public void testRemove() {
            remove();
        }
    }

    public static void main(String[] args) {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        TestItr itr = new TestItr(deque);

        // Case 1: lastRet == null -> expect IllegalStateException
        try {
            itr.testRemove();
            throw new Error("Expected IllegalStateException not thrown");
        } catch (IllegalStateException success) {
            // expected
        }

        // Prepare deque with one element and iterator with lastRet set
        deque.add(1);
        itr = new TestItr(deque);

        // Advance iterator to set lastRet
        Integer val = itr.next();
        if (val == null || val != 1) {
            throw new Error("Iterator next() did not return expected element");
        }

        // lastRet is set, call remove() should unlink the node
        itr.testRemove();

        // After removal, deque should be empty
        if (!deque.isEmpty()) {
            throw new Error("Deque should be empty after iterator remove");
        }

        // Calling remove() again without next() should throw IllegalStateException
        try {
            itr.testRemove();
            throw new Error("Expected IllegalStateException on second remove call not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
