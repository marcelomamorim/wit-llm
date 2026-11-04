/*
 * @test
 * @summary Deterministic tests for LinkedBlockingDeque's forEachRemaining method.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    public static void testForEachRemainingNullAction() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
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

    public static void testForEachRemainingNormal() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        deque.forEachRemaining(consumer);
        String result = sb.toString();
        if (!"ab".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }
}
