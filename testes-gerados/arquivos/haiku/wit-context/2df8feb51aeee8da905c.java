/*
 * @test
 * @summary ArrayBlockingQueue iterator remove() must throw IllegalStateException when no element was returned by next()
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Iterator;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveOnEmptyQueueIteratorThrows();
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesHead();
        testSecondRemoveWithoutNextThrows();
        testRemoveAfterNextThenNextContinues();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalStateOnRemove(Iterator<Integer> it, String message) {
        try {
            it.remove();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void testRemoveOnEmptyQueueIteratorThrows() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        Iterator<Integer> it = queue.iterator();
        expectIllegalStateOnRemove(it, "remove on empty queue iterator without next must throw IllegalStateException");
    }

    static void testRemoveWithoutNextThrows() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        Iterator<Integer> it = queue.iterator();
        expectIllegalStateOnRemove(it, "remove before next must throw IllegalStateException");
        check(queue.size() == 3, "queue size must be unchanged after failed remove");
        check(queue.peek() == 1, "head must be unchanged after failed remove");
    }

    static void testRemoveAfterNextRemovesHead() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        Iterator<Integer> it = queue.iterator();
        check(it.next() == 1, "next must return head element 1");
        it.remove();
        check(queue.size() == 2, "size must be 2 after removing via iterator");
        check(queue.peek() == 2, "new head must be 2");
        check(!queue.contains(1), "removed element 1 must not remain in queue");
    }

    static void testSecondRemoveWithoutNextThrows() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        queue.offer(1);
        queue.offer(2);
        Iterator<Integer> it = queue.iterator();
        it.next();
        it.remove();
        expectIllegalStateOnRemove(it, "second remove without intervening next must throw IllegalStateException");
        check(queue.size() == 1, "queue size must stay 1 after failed second remove");
        check(queue.peek() == 2, "remaining element must be 2");
    }

    static void testRemoveAfterNextThenNextContinues() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        Iterator<Integer> it = queue.iterator();
        check(it.next() == 1, "first next must return 1");
        it.remove();
        check(it.next() == 2, "next after remove must return 2");
        it.remove();
        check(queue.size() == 1, "size must be 1 after two iterator removals");
        check(queue.peek() == 3, "remaining element must be 3");
    }
}
