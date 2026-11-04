/*
 * @test
 * @summary Test java.util.ArrayDeque remove() method behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesElement();
    }

    static void testRemoveWithoutNextThrows() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        Iterator<String> it = deque.iterator();
        boolean threw = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when remove() called before next()");
        }
    }

    static void testRemoveAfterNextRemovesElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");
        Iterator<String> it = deque.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next element");
        }
        String first = it.next();
        if (!"one".equals(first)) {
            throw new RuntimeException("Expected first element to be 'one'");
        }
        it.remove();
        if (deque.contains("one")) {
            throw new RuntimeException("Element 'one' should have been removed");
        }
        // After remove, calling remove again without next should throw
        boolean threw = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when remove() called twice without next()");
        }
    }
}
