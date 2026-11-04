/*
 * @test
 * @summary Tests observable behavior of ArrayBlockingQueue.offer(E e)
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferNullThrowsNullPointerException();
        testOfferWhenNotFullReturnsTrueAndAddsElement();
        testOfferWhenFullReturnsFalseAndKeepsContents();
        testOfferPreservesFifoOrder();
    }

    static void testOfferNullThrowsNullPointerException() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        boolean thrown = false;
        try {
            q.offer(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("offer(null) did not throw NullPointerException");
        }
        if (!q.isEmpty()) {
            throw new RuntimeException("queue changed after offer(null)");
        }
    }

    static void testOfferWhenNotFullReturnsTrueAndAddsElement() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        if (!q.offer("a")) {
            throw new RuntimeException("offer returned false on non-full queue");
        }
        if (q.size() != 1) {
            throw new RuntimeException("unexpected size: " + q.size());
        }
        if (q.remainingCapacity() != 1) {
            throw new RuntimeException("unexpected remaining capacity: " + q.remainingCapacity());
        }
    }

    static void testOfferWhenFullReturnsFalseAndKeepsContents() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        if (!q.offer("first")) {
            throw new RuntimeException("initial offer failed");
        }
        if (q.offer("second")) {
            throw new RuntimeException("offer returned true on full queue");
        }
        if (q.size() != 1) {
            throw new RuntimeException("size changed after rejected offer: " + q.size());
        }
        if (!"first".equals(q.peek())) {
            throw new RuntimeException("head changed after rejected offer: " + q.peek());
        }
    }

    static void testOfferPreservesFifoOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        for (int i = 1; i <= 3; i++) {
            if (!q.offer(i)) {
                throw new RuntimeException("offer failed for " + i);
            }
        }
        if (q.offer(4)) {
            throw new RuntimeException("offer succeeded beyond capacity");
        }
        for (int expected = 1; expected <= 3; expected++) {
            Integer actual = q.poll();
            if (actual == null || actual.intValue() != expected) {
                throw new RuntimeException("expected " + expected + " but got " + actual);
            }
        }
        if (!q.isEmpty()) {
            throw new RuntimeException("queue not empty after draining");
        }
    }
}
