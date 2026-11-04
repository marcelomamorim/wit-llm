/* @test    @summary Test PriorityBlockingQueue.put() method for basic functionality and exception handling    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPutWithNormalElement();
        testPutWithNullElement();
    }

    static void testPutWithNormalElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.put(10);
        assert queue.size() == 1 : "Queue should contain one element after put";
        assert queue.peek() == 10 : "Element at head should be 10";
    }

    static void testPutWithNullElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.put(null);
            assert false : "Putting null should throw NullPointerException";
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
