/*
 * @test
 * @summary Checks Iterator.remove() behavior of LinkedTransferQueue through its public iterator
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<Integer> contents(LinkedTransferQueue<Integer> q) {
        List<Integer> result = new ArrayList<>();
        for (Integer i : q) {
            result.add(i);
        }
        return result;
    }

    static void testRemoveBeforeNextThrows() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(contents(q).equals(Arrays.asList(1, 2, 3)),
              "queue must be unchanged after failed remove()");
    }

    static void testRemoveAfterNextRemovesHead() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        check(it.next() == 1, "first next() must return 1");
        it.remove();
        check(contents(q).equals(Arrays.asList(2, 3)),
              "remove() after next() must remove the returned head element");
    }

    static void testDoubleRemoveThrows() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second consecutive remove() must throw IllegalStateException");
        check(contents(q).equals(Arrays.asList(2, 3)),
              "second remove() must not alter the queue");
    }

    static void testRemoveMiddleElement() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        it.next();
        check(it.next() == 2, "second next() must return 2");
        it.remove();
        check(contents(q).equals(Arrays.asList(1, 3)),
              "remove() must remove the element last returned by next()");
    }

    static void testRemoveAllViaIterator() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        check(q.isEmpty(), "removing all elements through iterator must empty the queue");
        check(q.size() == 0, "size must be 0 after removing all elements");
    }

    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveAfterNextRemovesHead();
        testDoubleRemoveThrows();
        testRemoveMiddleElement();
        testRemoveAllViaIterator();
    }
}
