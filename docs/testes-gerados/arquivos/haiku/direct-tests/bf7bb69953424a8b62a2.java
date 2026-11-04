/*
 * @test
 * @summary Verifies public observable behavior of ArrayBlockingQueue.peek(), including empty queue, non-removal, FIFO head, and wrap-around.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueuePeekReturnsNull();
        testPeekReturnsHeadWithoutRemoving();
        testPeekAfterPoll();
        testPeekAfterDrain();
        testPeekWrapAround();
        testFairQueuePeek();
    }

    static void testEmptyQueuePeekReturnsNull() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        check(q.peek() == null, "peek on empty queue must return null");
        check(q.size() == 0, "size must remain 0 after peek on empty queue");
    }

    static void testPeekReturnsHeadWithoutRemoving() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.offer("a");
        q.offer("b");
        check("a".equals(q.peek()), "peek must return head element");
        check(q.size() == 2, "peek must not remove the element");
        check("a".equals(q.peek()), "repeated peek must return same head");
        check(q.size() == 2, "size must stay unchanged after repeated peek");
    }

    static void testPeekAfterPoll() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.offer("a");
        q.offer("b");
        check("a".equals(q.poll()), "poll must return first element");
        check("b".equals(q.peek()), "peek must return next head after poll");
    }

    static void testPeekAfterDrain() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        q.offer("a");
        q.poll();
        check(q.peek() == null, "peek must return null after queue is drained");
    }

    static void testPeekWrapAround() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.offer("x");
        q.offer("y");
        q.offer("z");
        check("x".equals(q.poll()), "poll must return x");
        check("y".equals(q.poll()), "poll must return y");
        q.offer("w");
        check("z".equals(q.peek()), "peek must return z across wrap-around");
        check("z".equals(q.poll()), "poll must return z");
        check("w".equals(q.peek()), "peek must return w after wrap-around poll");
        check(q.size() == 1, "size must be 1 at the end");
    }

    static void testFairQueuePeek() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2, true);
        check(q.peek() == null, "fair queue peek on empty must return null");
        q.offer(Integer.valueOf(7));
        check(Integer.valueOf(7).equals(q.peek()), "fair queue peek must return head");
        check(q.size() == 1, "fair queue peek must not remove element");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
