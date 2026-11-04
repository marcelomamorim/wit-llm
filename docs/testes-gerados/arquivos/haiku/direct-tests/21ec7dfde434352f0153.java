/*
 * @test
 * @summary Observable behavior of ArrayBlockingQueue.poll() on empty, non-empty, wrapped and fair queues
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        pollOnEmptyReturnsNull();
        pollReturnsElementsInFifoOrder();
        pollReturnsNullAfterDrain();
        pollWorksWithFairQueue();
        pollReturnsInitialCollectionElementsInOrder();
        pollAfterWrapAroundKeepsFifoOrder();
    }

    static void pollOnEmptyReturnsNull() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        check(q.poll() == null, "poll on empty queue must return null");
        check(q.size() == 0, "size must remain 0 after poll on empty queue");
    }

    static void pollReturnsElementsInFifoOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        check(q.offer(1), "offer 1 should succeed");
        check(q.offer(2), "offer 2 should succeed");
        check(q.poll() == 1, "first poll must return first inserted element");
        check(q.size() == 1, "size must decrease by one after poll");
        check(q.poll() == 2, "second poll must return second inserted element");
        check(q.size() == 0, "size must be 0 after draining");
    }

    static void pollReturnsNullAfterDrain() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.offer(10);
        check(q.poll() == 10, "poll must return 10");
        check(q.poll() == null, "poll after drain must return null");
        check(q.isEmpty(), "queue must be empty after drain");
    }

    static void pollWorksWithFairQueue() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2, true);
        q.offer("a");
        q.offer("b");
        check("a".equals(q.poll()), "fair queue poll must return a first");
        check("b".equals(q.poll()), "fair queue poll must return b second");
        check(q.poll() == null, "fair queue poll on empty must return null");
    }

    static void pollReturnsInitialCollectionElementsInOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5, false, Arrays.asList(7, 8, 9));
        check(q.size() == 3, "initial collection must populate the queue");
        check(q.poll() == 7, "poll must return first element of initial collection");
        check(q.poll() == 8, "poll must return second element of initial collection");
        check(q.poll() == 9, "poll must return third element of initial collection");
        check(q.poll() == null, "poll after consuming initial collection must return null");
    }

    static void pollAfterWrapAroundKeepsFifoOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.offer(1);
        check(q.poll() == 1, "poll must return 1 before wrap-around");
        check(q.offer(2), "offer 2 should succeed");
        check(q.offer(3), "offer 3 should succeed");
        check(q.poll() == 2, "poll must return 2 after wrap-around");
        check(q.poll() == 3, "poll must return 3 after wrap-around");
        check(q.poll() == null, "poll must return null when empty after wrap-around");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
