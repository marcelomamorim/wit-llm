/*
 * @test
 * @summary Test PriorityBlockingQueue remove() method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutLastRet();
    }

    private static void testRemoveWithoutLastRet() {
        PriorityBlockingQueue<String> pbq = new PriorityBlockingQueue<>();
        pbq.add("a");

        // We cannot access lastRet directly, so we test that calling remove() without a valid lastRet throws IllegalStateException
        // The only way to have lastRet >= 0 is after calling iterator().next() and then remove() on iterator
        // But PriorityBlockingQueue.remove() is a public method with no parameters, so this test is limited

        // Since lastRet is private and no public setter, calling remove() directly on a new queue should throw IllegalStateException
        try {
            pbq.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
