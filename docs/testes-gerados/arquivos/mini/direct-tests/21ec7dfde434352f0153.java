/*
 * @test
 * @summary Test ArrayBlockingQueue.poll() method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testPollEmptyQueueReturnsNull();
        testPollReturnsElementAndRemovesIt();
    }

    static void testPollEmptyQueueReturnsNull() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        String result = queue.poll();
        if (result != null) {
            throw new AssertionError("poll() on empty queue should return null but returned: " + result);
        }
    }

    static void testPollReturnsElementAndRemovesIt() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        boolean offered = queue.offer("test");
        if (!offered) {
            throw new AssertionError("offer() should succeed on empty queue");
        }
        String polled = queue.poll();
        if (!"test".equals(polled)) {
            throw new AssertionError("poll() should return the offered element but returned: " + polled);
        }
        // After poll, queue should be empty
        String secondPoll = queue.poll();
        if (secondPoll != null) {
            throw new AssertionError("poll() after removing element should return null but returned: " + secondPoll);
        }
    }
}
