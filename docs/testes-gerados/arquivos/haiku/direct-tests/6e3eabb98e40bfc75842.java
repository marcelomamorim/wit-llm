/*
 * @test
 * @summary Verify ConcurrentLinkedQueue iterator remove() state checks and removal effects
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveWithoutNextThrows();
        testRemoveOnEmptyQueueIteratorThrows();
    }

    static void testRemoveWithoutNextThrows() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<>(Arrays.asList("a", "b", "c"));
        Iterator<String> it = q.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("remove() before next() must throw IllegalStateException");
        }
        if (q.size() != 3) {
            throw new RuntimeException("queue must be unchanged, size=" + q.size());
        }
    }

    static void testRemoveAfterNextRemovesElement() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<>(Arrays.asList("a", "b", "c"));
        Iterator<String> it = q.iterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("unexpected first element: " + first);
        }
        it.remove();
        if (q.size() != 2) {
            throw new RuntimeException("size after remove expected 2, got " + q.size());
        }
        if (q.contains("a")) {
            throw new RuntimeException("removed element still present");
        }
        if (!"b".equals(it.next())) {
            throw new RuntimeException("iteration must continue with b after remove");
        }
        if (!"c".equals(it.next())) {
            throw new RuntimeException("iteration must continue with c");
        }
        if (it.hasNext()) {
            throw new RuntimeException("iterator should be exhausted");
        }
    }

    static void testSecondRemoveWithoutNextThrows() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<>(Arrays.asList("a", "b", "c"));
        Iterator<String> it = q.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("second remove() without next() must throw IllegalStateException");
        }
        if (q.size() != 2) {
            throw new RuntimeException("second failed remove must not change queue, size=" + q.size());
        }
    }

    static void testRemoveOnEmptyQueueIteratorThrows() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        Iterator<String> it = q.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("remove() on empty-queue iterator must throw IllegalStateException");
        }
    }
}
