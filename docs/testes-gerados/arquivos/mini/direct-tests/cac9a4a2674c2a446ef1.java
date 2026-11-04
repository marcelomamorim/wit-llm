/*
 * @test
 * @summary Test ConcurrentLinkedDeque next() method behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Iterator;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testNextReturnsElementsInOrder();
        testNextThrowsOnEmptyIterator();
    }

    static void testNextReturnsElementsInOrder() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        Iterator<String> it = deque.iterator();

        String first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("Expected first element 'a', got: " + first);
        }

        String second = it.next();
        if (!"b".equals(second)) {
            throw new AssertionError("Expected second element 'b', got: " + second);
        }

        String third = it.next();
        if (!"c".equals(third)) {
            throw new AssertionError("Expected third element 'c', got: " + third);
        }
    }

    static void testNextThrowsOnEmptyIterator() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Iterator<String> it = deque.iterator();
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException on empty iterator");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
