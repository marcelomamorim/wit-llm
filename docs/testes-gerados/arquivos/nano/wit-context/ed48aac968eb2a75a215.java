/*
 * @test
 * @summary Test for PriorityBlockingQueue.forEachRemaining with null Consumer
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    static void testForEachRemainingWithNullConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        // Add some elements to avoid empty queue
        queue.add(1);
        queue.add(2);
        queue.add(3);
        try {
            queue.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testForEachRemainingWithValidConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(20);
        queue.add(30);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e).append(",");
        queue.forEachRemaining(consumer);
        String result = sb.toString();
        if (result.contains("10") && result.contains("20") && result.contains("30")) {
            System.out.println("PASS: Consumer processed all elements: " + result);
        } else {
            System.out.println("FAIL: Consumer did not process all elements: " + result);
        }
    }
}
