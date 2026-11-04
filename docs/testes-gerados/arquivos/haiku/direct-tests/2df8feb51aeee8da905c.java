/*
 * @test
 * @summary Verifies Iterator.remove() semantics of ArrayBlockingQueue: IllegalStateException when no element was returned by next(), and correct removal of the last returned element.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsISE();
        testRemoveAfterNextRemovesHead();
        testRemoveAfterNextRemovesMiddle();
        testSecondRemoveWithoutNextThrowsISE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveBeforeNextThrowsISE() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(5);
        q.add("a");
        q.add("b");
        Iterator<String> it = q.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(Arrays.toString(q.toArray()).equals("[a, b]"),
              "queue must be unchanged after failed remove()");
    }

    static void testRemoveAfterNextRemovesHead() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(5);
        q.add("a");
        q.add("b");
        q.add("c");
        Iterator<String> it = q.iterator();
        check("a".equals(it.next()), "first next() must return head");
        it.remove();
        check(q.size() == 2, "size must be 2 after remove, was " + q.size());
        check(Arrays.toString(q.toArray()).equals("[b, c]"),
              "unexpected contents: " + Arrays.toString(q.toArray()));
    }

    static void testRemoveAfterNextRemovesMiddle() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(5);
        q.add("a");
        q.add("b");
        q.add("c");
        Iterator<String> it = q.iterator();
        it.next();
        check("b".equals(it.next()), "second next() must return b");
        it.remove();
        check(q.size() == 2, "size must be 2 after remove, was " + q.size());
        check(Arrays.toString(q.toArray()).equals("[a, c]"),
              "unexpected contents: " + Arrays.toString(q.toArray()));
    }

    static void testSecondRemoveWithoutNextThrowsISE() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(5);
        q.add("a");
        q.add("b");
        Iterator<String> it = q.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without next() must throw IllegalStateException");
        check(Arrays.toString(q.toArray()).equals("[b]"),
              "only one element must be removed, contents: " + Arrays.toString(q.toArray()));
    }
}
