/*
 * @test
 * @summary Test ConcurrentLinkedDeque.forEach method for null argument and normal iteration
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachWithElements();
        testForEachWithEmptyDeque();
    }

    private static void testForEachNullAction() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean npeThrown = false;
        try {
            deque.forEach(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEach");
        }
    }

    private static void testForEachWithElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        deque.forEach(action);

        String result = sb.toString();
        // The order is not strictly guaranteed but for ConcurrentLinkedDeque it is FIFO order
        if (!result.contains("one") || !result.contains("two") || !result.contains("three")) {
            throw new AssertionError("forEach did not visit all elements: " + result);
        }
    }

    private static void testForEachWithEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        final boolean[] called = {false};
        deque.forEach(new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        });
        if (called[0]) {
            throw new AssertionError("forEach should not call action on empty deque");
        }
    }
}
