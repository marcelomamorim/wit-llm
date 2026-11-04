/*
 * @test
 * @summary Test for PriorityBlockingQueue.add method, covering null and non-null inputs.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testAddWithNull();
        testAddWithValidElement();
    }

    private static void testAddWithNull() {
        PriorityBlockingQueue<Object> queue = new PriorityBlockingQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.add(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null");
        }
    }

    private static void testAddWithValidElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean result = queue.add(42);
        if (!result || queue.isEmpty()) {
            throw new AssertionError("add should return true and queue should contain the element");
        }
        if (queue.peek() != 42) {
            throw new AssertionError("Queue top should be 42");
        }
    }
}
