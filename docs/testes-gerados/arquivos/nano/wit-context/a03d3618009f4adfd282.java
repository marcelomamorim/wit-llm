/* @test    @summary Test for ArrayDeque.tryAdvance with null Consumer    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyDeque();
        testTryAdvanceWithNonEmptyDeque();
    }

    static void testTryAdvanceWithNullConsumer() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        boolean exceptionThrown = false;
        try {
            deque.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        }
    }

    static void testTryAdvanceWithEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Consumer<Integer> consumer = (Integer e) -> {
            throw new AssertionError("Consumer should not be called on empty deque");
        };
        boolean result = deque.tryAdvance(consumer);
        if (result != false) {
            throw new AssertionError("tryAdvance should return false on empty deque");
        }
    }

    static void testTryAdvanceWithNonEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(42);
        final boolean[] consumerCalled = {false};
        Consumer<Integer> consumer = (Integer e) -> {
            if (e != 42) {
                throw new AssertionError("Consumer received wrong element");
            }
            consumerCalled[0] = true;
        };
        boolean result = deque.tryAdvance(consumer);
        if (result != true) {
            throw new AssertionError("tryAdvance should return true when element is processed");
        }
        if (!consumerCalled[0]) {
            throw new AssertionError("Consumer was not called");
        }
        // Verify that cursor advanced
        if (deque.cursor != deque.inc(0, deque.elements.length)) {
            throw new AssertionError("Cursor did not advance correctly");
        }
    }
}
