/*
 * @test
 * @summary Tests PriorityQueue.forEach: visits all elements, NPE on null action, CME on structural modification
 * @run main PriorityQueueWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testVisitsAllElements();
        testEmptyQueueNoCalls();
        testNullActionThrowsNPE();
        testNullActionOnEmptyQueueThrowsNPE();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testVisitsAllElements() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(5);
        pq.add(1);
        pq.add(4);
        pq.add(2);
        pq.add(3);
        List<Integer> seen = new ArrayList<>();
        pq.forEach(x -> seen.add(x));
        check(seen.size() == 5, "expected 5 visited elements, got " + seen.size());
        for (int i = 1; i <= 5; i++) {
            check(seen.contains(i), "element " + i + " not visited");
        }
        check(pq.size() == 5, "queue size changed by forEach");
    }

    static void testEmptyQueueNoCalls() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        int[] calls = new int[1];
        pq.forEach(s -> calls[0]++);
        check(calls[0] == 0, "action invoked on empty queue");
    }

    static void testNullActionThrowsNPE() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        boolean thrown = false;
        try {
            pq.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testNullActionOnEmptyQueueThrowsNPE() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean thrown = false;
        try {
            Consumer<Integer> c = null;
            pq.forEach(c);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action on empty queue");
    }

    static void testConcurrentModificationDetected() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        int[] calls = new int[1];
        boolean[] added = new boolean[1];
        boolean thrown = false;
        try {
            pq.forEach(x -> {
                calls[0]++;
                if (!added[0]) {
                    added[0] = true;
                    pq.add(10);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException");
        check(calls[0] == 3, "expected action invoked 3 times, got " + calls[0]);
    }
}
