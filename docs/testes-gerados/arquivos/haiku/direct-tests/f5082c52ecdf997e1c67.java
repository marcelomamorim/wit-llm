/*
 * @test
 * @summary Verifies Itr.remove() via the public iterator: ISE when next() was not called or remove was already called, and correct unlinking after next().
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveAfterNextRemovesHead();
        testRemoveTwiceThrows();
        testRemoveMiddleElement();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError(msg);
        }
    }

    static void testRemoveBeforeNextThrows() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2));
        Iterator<Integer> it = q.iterator();
        try {
            it.remove();
            throw new AssertionError("remove before next should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(q.size() == 2, "queue must be unchanged after failed remove");
    }

    static void testRemoveAfterNextRemovesHead() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        check(it.next() == 1, "first next must return 1");
        it.remove();
        check(q.size() == 2, "size must decrease by one");
        check(!q.contains(1), "removed element must not be present");
        check(q.toString().equals("[2, 3]"), "remaining order must be preserved");
    }

    static void testRemoveTwiceThrows() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new AssertionError("second remove without next should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(q.toString().equals("[2, 3]"), "second failed remove must not alter queue");
    }

    static void testRemoveMiddleElement() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        it.next();
        check(it.next() == 2, "second next must return 2");
        it.remove();
        check(q.toString().equals("[1, 3]"), "middle element must be removed");
        check(it.hasNext() && it.next() == 3, "iteration must continue after removed element");
    }
}
