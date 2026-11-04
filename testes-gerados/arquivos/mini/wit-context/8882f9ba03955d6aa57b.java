/*
 * @test
 * @summary Test PriorityBlockingQueue.forEach method for null action and normal iteration
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormalIteration();
    }

    static void testForEachNullAction() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        try {
            pq.forEach(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachNormalIteration() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(10);
        pq.add(5);
        pq.add(20);

        final StringBuilder sb = new StringBuilder();
        Consumer<Integer> action = new Consumer<Integer>() {
            public void accept(Integer i) {
                sb.append(i).append(',');
            }
        };

        pq.forEach(action);

        String result = sb.toString();
        if (result.isEmpty()) {
            throw new Error("forEach did not invoke action on any element");
        }
        // The order is not guaranteed, so just check that all elements appear
        if (!(result.contains("5") && result.contains("10") && result.contains("20"))) {
            throw new Error("forEach did not process all elements: " + result);
        }
    }
}
