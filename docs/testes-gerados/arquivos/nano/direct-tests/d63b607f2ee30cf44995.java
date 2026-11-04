/* @test    @summary Test for LinkedBlockingQueue.forEachRemaining method    @run main LinkedBlockingQueueWitupTest */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmpty();
        testForEachRemainingNullAction();
    }

    static void testForEachRemainingWithElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s);
        queue.forEachRemaining(consumer);
        assert result.toString().contains("a") : "Should contain 'a'";
        assert result.toString().contains("b") : "Should contain 'b'";
        assert result.toString().contains("c") : "Should contain 'c'";
    }

    static void testForEachRemainingEmpty() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s);
        queue.forEachRemaining(consumer); // should not throw
        assert result.length() == 0 : "Result should be empty";
    }

    static void testForEachRemainingNullAction() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("x");
        boolean exceptionThrown = false;
        try {
            queue.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException should be thrown";
    }
}
