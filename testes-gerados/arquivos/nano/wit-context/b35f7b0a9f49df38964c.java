/*
 * @test
 * @summary Test for ConcurrentLinkedDeque's forEachRemaining method to verify behavior with null Consumer.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithEmptyDeque();
        testForEachRemainingWithElements();
    }

    static void testForEachRemainingWithNullConsumer() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testForEachRemainingWithEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Consumer<String> consumer = s -> System.out.println("Consumed: " + s);
        try {
            deque.forEachRemaining(consumer);
            System.out.println("PASS: No exception with empty deque");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testForEachRemainingWithElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s).append(",");
        try {
            deque.forEachRemaining(consumer);
            String output = result.toString();
            if (output.contains("one") && output.contains("two") && output.contains("three")) {
                System.out.println("PASS: Elements consumed: " + output);
            } else {
                System.out.println("FAIL: Not all elements consumed: " + output);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
