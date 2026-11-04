/*
 * @test
 * @summary Test for ConcurrentLinkedDeque.forEach method to verify behavior with null Consumer.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithValidConsumer();
    }

    private static void testForEachWithNullConsumer() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        boolean exceptionThrown = false;
        try {
            deque.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEach");
        }
    }

    private static void testForEachWithValidConsumer() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        deque.forEach(consumer);
        String result = sb.toString();
        if (!result.contains("a") || !result.contains("b")) {
            throw new AssertionError("forEach did not process all elements: " + result);
        }
    }
}
