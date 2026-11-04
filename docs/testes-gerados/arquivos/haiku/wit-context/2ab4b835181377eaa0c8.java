/*
 * @test
 * @summary Witup-derived checks for the public Iterator.remove() contract of LinkedTransferQueue (Itr.remove backing the iterator)
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalState();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveWithoutNextThrowsIllegalState();
        testRemoveOnEmptyQueueIteratorThrowsIllegalState();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void expectIllegalState(Iterator<?> it, String message) {
        try {
            it.remove();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new AssertionError(message);
    }

    static void testRemoveBeforeNextThrowsIllegalState() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        Iterator<Integer> it = q.iterator();
        expectIllegalState(it, "remove() before next() must throw IllegalStateException");
        check(q.size() == 2, "queue must be unchanged after failed remove()");
    }

    static void testRemoveAfterNextRemovesElement() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(10);
        q.add(20);
        Iterator<Integer> it = q.iterator();
        Integer first = it.next();
        check(first.intValue() == 10, "first element must be 10");
        it.remove();
        check(q.size() == 1, "size must be 1 after removing one element");
        check(!q.contains(10), "removed element must no longer be present");
        check(q.contains(20), "other element must remain present");
    }

    static void testSecondRemoveWithoutNextThrowsIllegalState() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(5);
        q.add(6);
        Iterator<Integer> it = q.iterator();
        it.next();
        it.remove();
        expectIllegalState(it, "second remove() without next() must throw IllegalStateException");
        check(q.size() == 1, "queue size must not change after failed second remove()");
    }

    static void testRemoveOnEmptyQueueIteratorThrowsIllegalState() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        Iterator<Integer> it = q.iterator();
        expectIllegalState(it, "remove() on iterator without next() must throw IllegalStateException");
        check(q.isEmpty(), "empty queue must remain empty");
    }
}
