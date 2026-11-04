/*
 * @test
 * @summary Behavioral tests for ArrayBlockingQueue.peek() on empty, non-empty, polled, fair and collection-constructed queues
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPeekEmptyReturnsNull();
        testPeekReturnsHeadWithoutRemoving();
        testPeekAfterPoll();
        testPeekFairQueue();
        testPeekFromCollectionConstructor();
    }

    static void testPeekEmptyReturnsNull() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        check(q.peek() == null, "peek on empty queue must return null");
        check(q.size() == 0, "peek on empty queue must not change size");
    }

    static void testPeekReturnsHeadWithoutRemoving() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        check(q.offer("a"), "offer a");
        check(q.offer("b"), "offer b");
        check("a".equals(q.peek()), "peek must return head element");
        check("a".equals(q.peek()), "second peek must return same head element");
        check(q.size() == 2, "peek must not remove elements");
    }

    static void testPeekAfterPoll() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        q.offer("x");
        q.offer("y");
        check("x".equals(q.poll()), "poll must return x");
        check("y".equals(q.peek()), "peek after poll must return y");
        check(q.size() == 1, "size after poll and peek must be 1");
        q.poll();
        check(q.peek() == null, "peek after draining must return null");
    }

    static void testPeekFairQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2, true);
        check(q.peek() == null, "peek on empty fair queue must return null");
        q.offer(7);
        check(Integer.valueOf(7).equals(q.peek()), "peek on fair queue must return head");
        check(q.size() == 1, "peek on fair queue must not remove element");
    }

    static void testPeekFromCollectionConstructor() {
        ArrayBlockingQueue<String> q =
                new ArrayBlockingQueue<>(3, false, Arrays.asList("x", "y"));
        check("x".equals(q.peek()), "peek must return first element of initial collection");
        check(q.size() == 2, "peek must not remove initial collection elements");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
