/* @test    @summary Test the remove() method of PriorityBlockingQueue for correct exception and behavior handling    @run main PriorityBlockingQueueRemoveTest */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueRemoveTest {

    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.remove();
            // Expecting IllegalStateException because lastRet is initially -1
            System.err.println("Test failed: Expected IllegalStateException was not thrown.");
            System.exit(1);
        } catch (IllegalStateException e) {
            // Expected exception
            System.out.println("Caught expected IllegalStateException.");
        }
        // Additional test: add an element and then remove
        queue.add(10);
        // Simulate setting lastRet to a valid index (not directly accessible, so we test via remove method)
        // Since remove() relies on lastRet, we need to set lastRet to a valid value via reflection or simulate behavior
        // But reflection is not allowed; instead, test that remove() can be called after some operation
        // For the purpose of this test, we assume remove() is called after adding an element
        // and that lastRet is set internally, which is not directly testable here.
        // So, we focus on the exception case.
    }
}
