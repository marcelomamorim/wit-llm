/*
 * @test    @summary Test for forEachRemaining method in ConcurrentLinkedQueue
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        boolean exceptionThrown = false;
        try {
            queue.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachRemainingWithValidAction() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        queue.forEachRemaining(consumer);
        String result = sb.toString();
        if (!"ab".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }
}
