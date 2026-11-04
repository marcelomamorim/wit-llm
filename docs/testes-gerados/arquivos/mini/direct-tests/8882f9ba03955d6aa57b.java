/*
 * @test
 * @summary Test PriorityBlockingQueue.forEach method with various Consumers
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
    }

    static void testForEachWithValidConsumer() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        queue.forEach(consumer);

        String result = sb.toString();
        // The order is not guaranteed, but all elements must be present
        if (!(result.contains("one") && result.contains("two") && result.contains("three"))) {
            throw new AssertionError("forEach did not process all elements: " + result);
        }
    }

    static void testForEachWithNullConsumer() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        try {
            queue.forEach(null);
            throw new AssertionError("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
