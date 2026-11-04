/* @test    @summary Test PriorityQueue forEach method for correct iteration and exception handling    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;
import java.util.function.Consumer;
import java.util.ConcurrentModificationException;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testForEachNormal();
        testForEachConcurrentModification();
        testForEachNullAction();
    }

    static void testForEachNormal() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        StringBuilder sb = new StringBuilder();
        pq.forEach(e -> sb.append(e));
        boolean containsAll = sb.toString().contains("123") || sb.toString().contains("132") || sb.toString().contains("213") || sb.toString().contains("231") || sb.toString().contains("312") || sb.toString().contains("321");
        if (!containsAll) {
            throw new AssertionError("forEach did not process all elements correctly: " + sb.toString());
        }
    }

    static void testForEachConcurrentModification() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        try {
            pq.forEach(e -> {
                if (e == 2) {
                    pq.remove();
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        } catch (ConcurrentModificationException e) {
            // Expected exception
        }
    }

    static void testForEachNullAction() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        try {
            pq.forEach(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
