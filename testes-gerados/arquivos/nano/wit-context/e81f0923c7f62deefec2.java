/*
 * @test
 * @summary Test for ConcurrentLinkedQueue.forEach method to verify behavior with null Consumer.
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithValidConsumer();
    }

    private static void testForEachWithNullConsumer() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        boolean exceptionThrown = false;
        try {
            queue.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEach");
        }
    }

    private static void testForEachWithValidConsumer() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        queue.forEach(consumer);
        String result = sb.toString();
        if (!"ab".equals(result) && !"ba".equals(result)) {
            throw new AssertionError("Unexpected result from forEach: " + result);
        }
    }
}
