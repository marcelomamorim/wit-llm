/*
 * @test
 * @summary Witup-generated checks for PriorityQueue iterator remove() via public iterator()
 * @run main PriorityQueueWitupTest
 */

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.ConcurrentModificationException;

public class PriorityQueueWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void testRemoveBeforeNextThrowsIllegalState() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        Iterator<Integer> it = pq.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(pq.size() == 2, "queue must be unchanged after failed remove()");
    }

    static void testDoubleRemoveThrowsIllegalState() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(5);
        pq.add(3);
        pq.add(8);
        Iterator<Integer> it = pq.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without next() must throw IllegalStateException");
        check(pq.size() == 2, "size must be 2 after one successful iterator remove");
    }

    static void testRemoveAfterNextRemovesElement() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(10);
        pq.add(20);
        pq.add(30);
        Iterator<Integer> it = pq.iterator();
        Integer first = it.next();
        it.remove();
        check(pq.size() == 2, "size must decrease by one after iterator remove");
        check(!pq.contains(first), "removed element must no longer be in queue");
    }

    static void testConcurrentModificationThrows() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        Iterator<Integer> it = pq.iterator();
        it.next();
        pq.add(4);
        boolean thrown = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "remove() after external structural modification must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalState();
        testDoubleRemoveThrowsIllegalState();
        testRemoveAfterNextRemovesElement();
        testConcurrentModificationThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
