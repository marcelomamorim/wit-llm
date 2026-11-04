/*
 * @test
 * @summary Verifies LinkedBlockingDeque.remove() removes the head in FIFO order and throws NoSuchElementException when empty
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveReturnsHeadInOrder();
        testRemoveDecrementsSize();
        testRemoveOnEmptyThrowsNoSuchElementException();
        testRemoveAfterDrainingThrows();
    }

    static void testRemoveReturnsHeadInOrder() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        int a = d.remove();
        int b = d.remove();
        int c = d.remove();
        if (a != 1 || b != 2 || c != 3) {
            throw new RuntimeException("remove() did not return head in FIFO order: " + a + "," + b + "," + c);
        }
        if (!d.isEmpty()) {
            throw new RuntimeException("deque should be empty after removing all elements");
        }
    }

    static void testRemoveDecrementsSize() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.addLast(10);
        d.addLast(20);
        d.remove();
        if (d.size() != 1) {
            throw new RuntimeException("expected size 1 but was " + d.size());
        }
        int v = d.remove();
        if (v != 20) {
            throw new RuntimeException("expected remaining element 20 but was " + v);
        }
    }

    static void testRemoveOnEmptyThrowsNoSuchElementException() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        try {
            d.remove();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new RuntimeException("remove() on empty deque did not throw NoSuchElementException");
    }

    static void testRemoveAfterDrainingThrows() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.addLast(5);
        d.remove();
        try {
            d.remove();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new RuntimeException("remove() after draining deque did not throw NoSuchElementException");
    }
}
