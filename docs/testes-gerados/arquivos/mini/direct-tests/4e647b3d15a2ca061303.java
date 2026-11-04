/*
 * @test
 * @summary Test PriorityQueue.forEach method behavior including normal iteration and concurrent modification detection
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.Consumer;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testForEachProcessesAllElements();
        testForEachThrowsOnNullAction();
        testForEachThrowsOnConcurrentModification();
    }

    private static void testForEachProcessesAllElements() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("apple");
        pq.add("banana");
        pq.add("cherry");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        pq.forEach(action);

        String result = sb.toString();
        // The order is not guaranteed, but all elements must be present
        if (!(result.contains("apple") && result.contains("banana") && result.contains("cherry"))) {
            throw new AssertionError("forEach did not process all elements: " + result);
        }
    }

    private static void testForEachThrowsOnNullAction() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("element");
        try {
            pq.forEach(null);
            throw new AssertionError("forEach should throw NullPointerException when action is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachThrowsOnConcurrentModification() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("one");
        pq.add("two");

        Consumer<String> action = new Consumer<String>() {
            private boolean first = true;
            public void accept(String s) {
                if (first) {
                    first = false;
                    pq.add("three"); // modifies the queue during iteration
                }
            }
        };

        try {
            pq.forEach(action);
            throw new AssertionError("forEach should throw ConcurrentModificationException on concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
