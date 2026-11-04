/*
 * @test
 * @summary Verify ArrayDeque iterator next() returns elements in order and throws NoSuchElementException when no remaining elements exist
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNextOnEmptyDequeThrows();
        testNextAfterExhaustionThrows();
        testNextReturnsElementsInOrder();
        testNextOnNonEmptyDequeDoesNotThrowAtStart();
    }

    static void testNextOnEmptyDequeThrows() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        Iterator<String> it = deque.iterator();
        expectNoSuchElement(it);
    }

    static void testNextAfterExhaustionThrows() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        Iterator<String> it = deque.iterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("expected a but was " + first);
        }
        expectNoSuchElement(it);
    }

    static void testNextReturnsElementsInOrder() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("x");
        deque.addLast("y");
        deque.addLast("z");
        Iterator<String> it = deque.iterator();
        assertEquals("x", it.next());
        assertEquals("y", it.next());
        assertEquals("z", it.next());
        if (it.hasNext()) {
            throw new AssertionError("iterator should be exhausted");
        }
        expectNoSuchElement(it);
    }

    static void testNextOnNonEmptyDequeDoesNotThrowAtStart() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(4);
        deque.add(Integer.valueOf(7));
        Iterator<Integer> it = deque.iterator();
        if (!it.hasNext()) {
            throw new AssertionError("iterator should have an element");
        }
        assertEquals(Integer.valueOf(7), it.next());
    }

    static void expectNoSuchElement(Iterator<?> it) {
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new AssertionError("expected NoSuchElementException");
    }

    static void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }
}
