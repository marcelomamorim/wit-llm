/*
 * @test
 * @summary Verifies that LinkedBlockingQueue(int capacity) rejects non-positive capacities and honors positive capacity bounds
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        checkRejects(0);
        checkRejects(-1);
        checkRejects(Integer.MIN_VALUE);
        checkBoundedBehavior();
    }

    static void checkRejects(int capacity) {
        boolean thrown = false;
        try {
            new LinkedBlockingQueue<String>(capacity);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for capacity " + capacity);
        }
    }

    static void checkBoundedBehavior() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(2);
        if (q.remainingCapacity() != 2) {
            throw new RuntimeException("Expected remainingCapacity 2, got " + q.remainingCapacity());
        }
        if (!q.offer("a")) {
            throw new RuntimeException("offer(a) should succeed");
        }
        if (!q.offer("b")) {
            throw new RuntimeException("offer(b) should succeed");
        }
        if (q.offer("c")) {
            throw new RuntimeException("offer(c) should fail when queue is full");
        }
        if (q.size() != 2) {
            throw new RuntimeException("Expected size 2, got " + q.size());
        }
        if (q.remainingCapacity() != 0) {
            throw new RuntimeException("Expected remainingCapacity 0, got " + q.remainingCapacity());
        }
        if (!"a".equals(q.poll())) {
            throw new RuntimeException("Expected FIFO head 'a'");
        }
    }
}
