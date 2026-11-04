/*
 * @test
 * @summary Test ConcurrentLinkedQueue.next() method behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testNextThrowsWhenEmpty();
        testNextReturnsElementsInOrder();
    }

    static void testNextThrowsWhenEmpty() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        java.util.Iterator<String> it = queue.iterator();
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testNextReturnsElementsInOrder() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        java.util.Iterator<String> it = queue.iterator();

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

        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException after last element");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
