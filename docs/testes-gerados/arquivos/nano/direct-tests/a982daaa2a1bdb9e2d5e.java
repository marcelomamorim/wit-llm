/* @test    @summary Test for ArrayDeque.next() method behavior    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNextReturnsElement();
        testNextThrowsExceptionWhenEmpty();
    }

    static void testNextReturnsElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("element1");
        deque.add("element2");
        // Manually set cursor to 0 for testing
        // Since cursor is private, simulate by calling next() after removing all elements
        // or by subclassing, but for simplicity, we test the method directly via reflection
        // However, reflection is not allowed; instead, we test the behavior assuming cursor at 0
        // For this, we can simulate by creating a subclass with access to cursor, but as per constraints,
        // we test the method indirectly by calling next() on a deque with known state.
        // Since next() is final and no public cursor, we test by calling next() after advancing the iterator.
        // But the method is not public, so we test the behavior by invoking next() via a wrapper.
        // Alternatively, we can test the method's effect by calling it on a deque with known elements.
        // For simplicity, we test that calling next() returns the first element after setup.
        // Note: In real scenario, cursor management is internal; here, we test the method's exception and return.
        try {
            String first = deque.iterator().next();
            assert first.equals("element1") : "Expected element1";
        } catch (NoSuchElementException e) {
            assert false : "Should not throw NoSuchElementException when elements are present";
        }
    }

    static void testNextThrowsExceptionWhenEmpty() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.iterator().next();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
