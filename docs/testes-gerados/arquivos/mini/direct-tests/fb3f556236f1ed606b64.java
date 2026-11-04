/*
 * @test
 * @summary Test LinkedBlockingQueue.forEachRemaining method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithEmptyQueue();
        testForEachRemainingWithElements();
    }

    static void testForEachRemainingWithNullAction() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.iterator().forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        queue.iterator().forEachRemaining(action);
        if (sb.length() != 0) {
            throw new RuntimeException("forEachRemaining on empty queue should not invoke action");
        }
    }

    static void testForEachRemainingWithElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        queue.iterator().forEachRemaining(action);
        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new RuntimeException("forEachRemaining did not process all elements in order: " + result);
        }
        // Subsequent call should not invoke action again
        sb.setLength(0);
        queue.iterator().forEachRemaining(action);
        if (sb.length() != 0) {
            throw new RuntimeException("forEachRemaining called twice should not invoke action second time");
        }
    }
}
