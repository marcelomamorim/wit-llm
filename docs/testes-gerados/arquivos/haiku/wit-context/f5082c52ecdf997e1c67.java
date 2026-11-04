/*
 * @test
 * @summary Witup-derived tests for LinkedBlockingQueue iterator remove() preconditions and observable effects
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalStateException();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveWithoutNextThrowsIllegalStateException();
        testRemoveAfterExhaustionAfterNextRemovesLastElement();
        System.out.println("PASSED");
    }

    static void testRemoveBeforeNextThrowsIllegalStateException() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        Iterator<String> it = q.iterator();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
        if (q.size() != 1 || !"a".equals(q.peek())) {
            throw new RuntimeException("queue modified by failed remove");
        }
    }

    static void testRemoveAfterNextRemovesElement() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        Iterator<String> it = q.iterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("unexpected first element: " + first);
        }
        it.remove();
        if (q.size() != 2) {
            throw new RuntimeException("size after remove: " + q.size());
        }
        if (!"b".equals(q.peek())) {
            throw new RuntimeException("unexpected head after remove: " + q.peek());
        }
        if (q.contains("a")) {
            throw new RuntimeException("removed element still present");
        }
    }

    static void testSecondRemoveWithoutNextThrowsIllegalStateException() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        Iterator<String> it = q.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
        }
        if (q.size() != 1 || !"b".equals(q.peek())) {
            throw new RuntimeException("queue state changed by failed second remove");
        }
    }

    static void testRemoveAfterExhaustionAfterNextRemovesLastElement() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("x");
        Iterator<String> it = q.iterator();
        it.next();
        if (it.hasNext()) {
            throw new RuntimeException("iterator should be exhausted");
        }
        it.remove();
        if (!q.isEmpty()) {
            throw new RuntimeException("queue should be empty, size=" + q.size());
        }
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
        }
    }
}
