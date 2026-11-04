/*
 * @test
 * @summary Testa o método forEachRemaining de LinkedBlockingDeque para comportamento observável.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithEmptyDeque();
        testForEachRemainingWithElements();
    }

    static void testForEachRemainingWithNullAction() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachRemainingWithEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        final boolean[] called = {false};
        deque.forEachRemaining(e -> {
            called[0] = true;
        });
        if (called[0]) {
            throw new AssertionError("Action should not be called for empty deque");
        }
    }

    static void testForEachRemainingWithElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        final StringBuilder sb = new StringBuilder();
        deque.forEachRemaining(e -> sb.append(e));
        String result = sb.toString();
        if (!"123".equals(result)) {
            throw new AssertionError("Expected '123', but got: " + result);
        }
    }
}
