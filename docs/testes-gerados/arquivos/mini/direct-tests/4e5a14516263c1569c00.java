/*
 * @test
 * @summary Test ArrayBlockingQueue.forEachRemaining with various Consumers
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.concurrent.BlockingQueue;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        queue.add("one");
        queue.add("two");
        queue.add("three");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        // Use iterator and call forEachRemaining on it
        java.util.Iterator<String> it = queue.iterator();
        it.forEachRemaining(consumer);

        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two") || !result.contains("three")) {
            throw new AssertionError("forEachRemaining did not process all elements: " + result);
        }

        // After forEachRemaining, iterator should have no more elements
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no more elements after forEachRemaining");
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.add("a");

        java.util.Iterator<String> it = queue.iterator();

        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
