/*
 * @test
 * @summary Verifies LinkedBlockingDeque.remove() removes the head element and throws NoSuchElementException when the deque is empty
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testEmptyRemoveThrows();
        testFifoOrderWithAdd();
        testRemoveTakesFirst();
        testBoundedCapacityFreedByRemove();
        testFromCollection();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyRemoveThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            deque.remove();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "remove() on empty deque must throw NoSuchElementException");
        check(deque.isEmpty(), "deque must remain empty after failed remove()");
    }

    static void testFifoOrderWithAdd() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        check(deque.remove().equals(1), "first remove() should return 1");
        check(deque.remove().equals(2), "second remove() should return 2");
        check(deque.remove().equals(3), "third remove() should return 3");
        check(deque.isEmpty(), "deque should be empty after removing all elements");

        boolean thrown = false;
        try {
            deque.remove();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "remove() after draining must throw NoSuchElementException");
    }

    static void testRemoveTakesFirst() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("b");
        deque.addFirst("a");
        deque.addLast("c");
        check(deque.remove().equals("a"), "remove() should take the head (addFirst element)");
        check(deque.size() == 2, "size should be 2 after one remove()");
        check(deque.peekFirst().equals("b"), "new head should be \"b\"");
    }

    static void testBoundedCapacityFreedByRemove() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);
        check(deque.offer("a"), "offer to non-full deque should succeed");
        check(deque.offer("b"), "offer to non-full deque should succeed");
        check(!deque.offer("c"), "offer to full deque should return false");
        check(deque.remove().equals("a"), "remove() should return \"a\"");
        check(deque.offer("c"), "offer should succeed after remove() freed capacity");
        check(deque.remove().equals("b"), "next remove() should return \"b\"");
        check(deque.remove().equals("c"), "last remove() should return \"c\"");
    }

    static void testFromCollection() {
        LinkedBlockingDeque<String> deque =
            new LinkedBlockingDeque<>(Arrays.asList("x", "y"));
        check(deque.remove().equals("x"), "remove() should return first element of source collection");
        check(deque.remove().equals("y"), "remove() should return second element of source collection");
        boolean thrown = false;
        try {
            deque.remove();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "remove() after draining collection-built deque must throw");
    }
}
