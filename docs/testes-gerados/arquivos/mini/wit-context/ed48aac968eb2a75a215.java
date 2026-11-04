/*
 * @test
 * @summary Test PriorityBlockingQueue.forEachRemaining(Consumer) for NPE on null action and normal iteration
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        try {
            pq.forEachRemaining(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(10);
        pq.add(20);
        pq.add(30);

        final StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = new Consumer<Integer>() {
            public void accept(Integer i) {
                sb.append(i).append(",");
            }
        };

        pq.forEachRemaining(consumer);

        String result = sb.toString();
        // The order is not guaranteed, but all elements must be visited exactly once
        // Check that all elements appear exactly once
        if (!result.contains("10,") || !result.contains("20,") || !result.contains("30,")) {
            throw new Error("forEachRemaining did not visit all elements: " + result);
        }
        // Check no duplicates by counting commas
        int count = 0;
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == ',') count++;
        }
        if (count != 3) {
            throw new Error("forEachRemaining visited elements multiple times or missed some: " + result);
        }
    }
}
