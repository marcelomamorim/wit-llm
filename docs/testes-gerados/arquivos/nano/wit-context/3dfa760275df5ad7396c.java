/*
 * @test
 * @summary Tests for java.util.concurrent.LinkedBlockingDeque.tryAdvance method.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyDeque();
        testTryAdvanceWithElement();
        testTryAdvanceWithMultipleElements();
        testTryAdvanceWithConcurrentModification();
    }

    static void testTryAdvanceWithNullAction() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testTryAdvanceWithEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Consumer<String> consumer = s -> {};
        boolean result = deque.tryAdvance(consumer);
        if (result) {
            throw new AssertionError("tryAdvance should return false on empty deque");
        }
    }

    static void testTryAdvanceWithElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element");
        final boolean[] accepted = {false};
        Consumer<String> consumer = s -> {
            if ("element".equals(s)) {
                accepted[0] = true;
            }
        };
        boolean result = deque.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when element is present");
        }
        if (!accepted[0]) {
            throw new AssertionError("Consumer did not accept the element");
        }
    }

    static void testTryAdvanceWithMultipleElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        final String[] acceptedElement = {null};
        Consumer<String> consumer = s -> {
            acceptedElement[0] = s;
        };
        boolean result = deque.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when elements are present");
        }
        if (acceptedElement[0] == null || (!"first".equals(acceptedElement[0]) && !"second".equals(acceptedElement[0]))) {
            throw new AssertionError("Consumer did not accept the expected element");
        }
    }

    static void testTryAdvanceWithConcurrentModification() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("initial");
        final boolean[] accepted = {false};
        Consumer<String> consumer = s -> {
            if ("initial".equals(s)) {
                accepted[0] = true;
            }
        };
        // Simulate concurrent modification by removing element during iteration
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(10);
                deque.remove("initial");
            } catch (InterruptedException e) {
                // ignore
            }
        });
        t.start();
        boolean result = deque.tryAdvance(consumer);
        t.join();
        // The method should handle concurrent modification gracefully
        if (result && !accepted[0]) {
            throw new AssertionError("Consumer did not accept the element during concurrent modification");
        }
    }
}
