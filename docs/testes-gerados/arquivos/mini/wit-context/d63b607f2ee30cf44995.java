/*
 * @test
 * @summary Test LinkedBlockingQueue.forEachRemaining with null and normal Consumer
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithNull();
        testForEachRemainingWithConsumer();
    }

    static void testForEachRemainingWithNull() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // The forEachRemaining method should throw NullPointerException if action is null
        try {
            queue.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithConsumer() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        queue.iterator().forEachRemaining(consumer); // iterator's forEachRemaining

        // The queue's forEachRemaining is not public, but we can test it via iterator
        // The queue iterator should visit all elements in order
        String result = sb.toString();
        if (!result.equals("one,two,three,")) {
            throw new AssertionError("Unexpected iteration result: " + result);
        }
    }
}
