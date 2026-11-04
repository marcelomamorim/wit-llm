/*
 * @test
 * @summary Test the poll() method of ArrayBlockingQueue for correct behavior.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPollOnEmptyQueue();
        testPollOnNonEmptyQueue();
    }

    static void testPollOnEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        String result = queue.poll();
        if (result != null) {
            throw new AssertionError("Expected null when polling empty queue, but got: " + result);
        }
    }

    static void testPollOnNonEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.offer("element1");
        String polled = queue.poll();
        if (!"element1".equals(polled)) {
            throw new AssertionError("Expected 'element1', but got: " + polled);
        }
        // After polling, queue should be empty
        if (queue.poll() != null) {
            throw new AssertionError("Expected null after polling the only element");
        }
    }
}
