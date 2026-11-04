/*
 * @test
 * @summary Test LinkedBlockingDeque remove() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesElement();
    }

    // Test that calling remove() before next() throws IllegalStateException
    private static void testRemoveWithoutNextThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        Iterator<String> it = deque.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when calling remove() before next()");
        }
    }

    // Test that calling remove() after next() removes the element from the deque
    private static void testRemoveAfterNextRemovesElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        Iterator<String> it = deque.iterator();

        String first = it.next();
        if (!"one".equals(first)) {
            throw new RuntimeException("Expected first element to be 'one'");
        }

        it.remove(); // should remove "one"

        // After removal, "one" should no longer be in deque
        if (deque.contains("one")) {
            throw new RuntimeException("Element 'one' was not removed by iterator.remove()");
        }

        // The next element should be "two"
        String second = it.next();
        if (!"two".equals(second)) {
            throw new RuntimeException("Expected second element to be 'two'");
        }

        it.remove(); // remove "two"

        if (!deque.isEmpty()) {
            throw new RuntimeException("Deque should be empty after removing all elements");
        }
    }
}
