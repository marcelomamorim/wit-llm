/*
 * @test
 * @summary Tests for PriorityQueue.forEach method, focusing on exception paths.
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.function.Consumer;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testForEachNullAction();
        testForEachConcurrentModificationException();
    }

    private static void testForEachNullAction() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        try {
            pq.forEach(null);
            System.out.println("testForEachNullAction: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testForEachNullAction: Passed");
        } catch (Throwable t) {
            System.out.println("testForEachNullAction: Failed - unexpected exception: " + t);
        }
    }

    private static void testForEachConcurrentModificationException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                pq.add(3); // Modifies the queue during iteration
            }
        };
        try {
            pq.forEach(consumer);
            System.out.println("testForEachConcurrentModificationException: Failed - no exception thrown");
        } catch (ConcurrentModificationException e) {
            System.out.println("testForEachConcurrentModificationException: Passed");
        } catch (Throwable t) {
            System.out.println("testForEachConcurrentModificationException: Failed - unexpected exception: " + t);
        }
    }
}
