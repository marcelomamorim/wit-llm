/* @test    @summary Test for PriorityBlockingQueue.remove() method to verify exception handling and behavior.    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveWithInvalidState();
        testRemoveWithValidState();
    }

    static void testRemoveWithInvalidState() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        // Using reflection or internal state is fragile; instead, simulate invalid state by setting lastRet to < 0
        // Since lastRet is private, we cannot set it directly; instead, we test the behavior when remove() is called without prior iteration
        try {
            queue.remove();
            // Expecting IllegalStateException as per method contract when lastRet < 0
            // But in actual implementation, remove() does not check lastRet directly; it throws if lastRet < 0
            // Since lastRet is internal, simulate by calling remove() without prior iteration, which should throw
            // The method as per source code throws if lastRet < 0
            // So, this test is valid if remove() is called without prior iteration
            // No setup needed, just call remove()
            System.out.println("testRemoveWithInvalidState: Failed - no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testRemoveWithInvalidState: Passed - caught expected IllegalStateException");
        } catch (Exception e) {
            System.out.println("testRemoveWithInvalidState: Failed - caught unexpected exception: " + e);
        }
    }

    static void testRemoveWithValidState() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        // Remove an element to set lastRet to a valid index
        // Since lastRet is internal, simulate by calling iterator().next() and then remove()
        try {
            java.util.Iterator<Integer> it = queue.iterator();
            if (it.hasNext()) {
                it.next();
                it.remove();
                System.out.println("testRemoveWithValidState: Passed - remove() executed without exception");
            } else {
                System.out.println("testRemoveWithValidState: Failed - iterator has no elements");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("testRemoveWithValidState: Failed - unexpected UnsupportedOperationException");
        } catch (Exception e) {
            System.out.println("testRemoveWithValidState: Failed - caught exception: " + e);
        }
    }
}
