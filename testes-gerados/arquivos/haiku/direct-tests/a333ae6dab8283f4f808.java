/*
 * @test
 * @summary Verify LinkedBlockingQueue.peek returns the head element without removing it and returns null when empty
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyQueueReturnsNull();
        testPeekReturnsHeadWithoutRemoving();
        testPeekAfterPollReturnsNextElement();
        testPeekAfterClearReturnsNull();
        testPeekOnBoundedQueue();
        testPeekAfterDrainAndRefill();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueReturnsNull() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        check(q.peek() == null, "peek on empty queue must return null");
        check(q.size() == 0, "size must remain 0 after peek on empty queue");
    }

    static void testPeekReturnsHeadWithoutRemoving() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.offer(1);
        q.offer(2);
        q.offer(3);
        check(q.peek() == 1, "peek must return head element 1");
        check(q.peek() == 1, "repeated peek must return same head element");
        check(q.size() == 3, "peek must not remove elements");
    }

    static void testPeekAfterPollReturnsNextElement() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.offer(10);
        q.offer(20);
        check(q.poll() == 10, "poll must return 10");
        check(q.peek() == 20, "peek must return next head 20 after poll");
        check(q.size() == 1, "size must be 1 after poll");
    }

    static void testPeekAfterClearReturnsNull() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.offer(5);
        q.offer(6);
        q.clear();
        check(q.peek() == null, "peek after clear must return null");
        check(q.isEmpty(), "queue must be empty after clear");
    }

    static void testPeekOnBoundedQueue() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(2);
        check(q.offer("a"), "first offer must succeed");
        check(q.offer("b"), "second offer must succeed");
        check(!q.offer("c"), "offer beyond capacity must return false");
        check("a".equals(q.peek()), "peek must return head \"a\" on bounded queue");
        check(q.size() == 2, "size must remain 2 after peek");
    }

    static void testPeekAfterDrainAndRefill() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(1);
        q.offer(7);
        check(q.poll() == 7, "poll must return 7");
        check(q.peek() == null, "peek must return null after draining");
        q.offer(8);
        check(q.peek() == 8, "peek must return newly inserted element 8");
    }
}
