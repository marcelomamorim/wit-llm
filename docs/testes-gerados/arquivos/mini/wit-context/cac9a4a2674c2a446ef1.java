/*
 * @test
 * @summary Test ConcurrentLinkedDeque.next() method behavior including NoSuchElementException
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Iterator;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testNextReturnsElement();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextReturnsElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("first");
        Iterator<String> it = deque.iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        String element = it.next();
        if (!"first".equals(element)) {
            throw new AssertionError("Expected 'first' but got: " + element);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Iterator<String> it = deque.iterator();
        if (it.hasNext()) {
            throw new AssertionError("Iterator should not have next element");
        }
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
