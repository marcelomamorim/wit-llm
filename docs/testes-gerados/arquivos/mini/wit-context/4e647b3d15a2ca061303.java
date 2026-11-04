/*
 * @test
 * @summary Deterministic tests for java.util.PriorityQueue.forEach(Consumer)
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachConcurrentModificationException();
        testForEachNormalBehavior();
    }

    static void testForEachNullAction() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        try {
            pq.forEach(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachConcurrentModificationException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(10);
        pq.add(20);
        pq.add(30);

        Consumer<Integer> action = new Consumer<Integer>() {
            int count = 0;
            public void accept(Integer i) {
                count++;
                if (count == 1) {
                    // Modify the queue during iteration to trigger ConcurrentModificationException
                    pq.add(40);
                }
            }
        };

        try {
            pq.forEach(action);
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testForEachNormalBehavior() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        pq.add("b");
        pq.add("c");

        final StringBuilder sb = new StringBuilder();
        pq.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        if (result.length() != 3 || !result.contains("a") || !result.contains("b") || !result.contains("c")) {
            throw new Error("forEach did not visit all elements: " + result);
        }
    }
}
