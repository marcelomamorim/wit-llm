/*
 * @test
 * @summary Verifies that Iterator.remove() of ConcurrentLinkedQueue throws IllegalStateException when no element was returned by next() and after a repeated remove, and that a successful remove unlinks the returned element.
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveTwiceThrows();
        testRemoveAfterNextRemovesElement();
    }

    static void testRemoveBeforeNextThrows() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        Iterator<String> it = q.iterator();
        expectIllegalState(it);
        if (q.size() != 1 || !"a".equals(q.peek())) {
            throw new RuntimeException("queue changed after failed remove");
        }
    }

    static void testRemoveTwiceThrows() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        Iterator<String> it = q.iterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("unexpected first element: " + first);
        }
        it.remove();
        expectIllegalState(it);
    }

    static void testRemoveAfterNextRemovesElement() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        Iterator<String> it = q.iterator();
        it.next();
        it.remove();
        if (q.size() != 1) {
            throw new RuntimeException("expected size 1 but was " + q.size());
        }
        if (!"b".equals(q.peek())) {
            throw new RuntimeException("expected head b but was " + q.peek());
        }
        if (!it.hasNext() || !"b".equals(it.next())) {
            throw new RuntimeException("iterator should still return b");
        }
    }

    static void expectIllegalState(Iterator<String> it) {
        try {
            it.remove();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException");
    }
}
