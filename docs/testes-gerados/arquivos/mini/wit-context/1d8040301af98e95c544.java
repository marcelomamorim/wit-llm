/*
 * @test
 * @summary Test ConcurrentLinkedDeque.AbstractItr.remove() behavior including IllegalStateException
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesElement();
    }

    private static void testRemoveWithoutNextThrows() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        Iterator<String> it = deque.iterator();
        boolean threw = false;
        try {
            it.remove(); // remove called before next
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when remove() called before next()");
        }
    }

    private static void testRemoveAfterNextRemovesElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        deque.add("two");
        Iterator<String> it = deque.iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        String first = it.next();
        if (!"one".equals(first) && !"two".equals(first)) {
            throw new AssertionError("Unexpected first element: " + first);
        }
        it.remove(); // should remove the element just returned by next()
        // Verify element removed from deque
        if (deque.contains(first)) {
            throw new AssertionError("Element was not removed from deque after remove()");
        }
        // Subsequent remove without next should throw
        boolean threw = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when remove() called twice without next()");
        }
    }
}
