/*
 * @test
 * @summary Checks Itr.remove() state handling reached through PriorityBlockingQueue.iterator()
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveBeforeNextOnEmptyQueueThrowsISE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Iterator<Integer> it = q.iterator();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void testRemoveBeforeNextOnNonEmptyQueueThrowsISE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        Iterator<Integer> it = q.iterator();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
        check(q.size() == 2, "queue must be unchanged after rejected remove");
    }

    static void testRemoveAfterNextRemovesElement() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        Iterator<Integer> it = q.iterator();
        Integer first = it.next();
        check(first.intValue() == 1, "next() should return least element first");
        it.remove();
        check(q.size() == 1, "remove() should delete one element");
        check(q.peek().intValue() == 3, "remaining element should be 3");
    }

    static void testSecondRemoveWithoutNextThrowsISE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(2);
        Iterator<Integer> it = q.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
        }
        check(q.size() == 1, "second rejected remove must not change queue");
    }

    public static void main(String[] args) {
        testRemoveBeforeNextOnEmptyQueueThrowsISE();
        testRemoveBeforeNextOnNonEmptyQueueThrowsISE();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveWithoutNextThrowsISE();
    }
}
