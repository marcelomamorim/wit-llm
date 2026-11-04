/*
 * @test
 * @summary Test ConcurrentLinkedQueue.forEach method behavior including null action
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
    }

    private static void testForEachWithValidConsumer() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        queue.forEach(action);

        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two") || !result.contains("three")) {
            throw new AssertionError("forEach did not process all elements: " + result);
        }
    }

    private static void testForEachWithNullConsumer() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        boolean npeThrown = false;
        try {
            queue.forEach(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("forEach should throw NullPointerException when action is null");
        }
    }
}
