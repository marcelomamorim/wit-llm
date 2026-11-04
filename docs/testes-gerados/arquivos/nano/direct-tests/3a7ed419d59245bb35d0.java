/* @test    @summary Test removeIf method of PriorityQueue for various predicates    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;
import java.util.function.Predicate;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfWithNullPredicate();
        testRemoveIfWithMatchingPredicate();
        testRemoveIfWithNonMatchingPredicate();
        testRemoveIfOnEmptyQueue();
    }

    static void testRemoveIfWithNullPredicate() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        try {
            pq.removeIf(null);
            System.out.println("Failed: Expected NullPointerException for null predicate");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        }
    }

    static void testRemoveIfWithMatchingPredicate() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(2);
        pq.offer(4);
        pq.offer(6);
        boolean removed = pq.removeIf(n -> n % 2 == 0);
        if (removed && pq.isEmpty()) {
            System.out.println("Passed: All even elements removed successfully");
        } else {
            System.out.println("Failed: Not all even elements removed");
        }
    }

    static void testRemoveIfWithNonMatchingPredicate() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        pq.offer(3);
        pq.offer(5);
        boolean removed = pq.removeIf(n -> n > 10);
        if (!removed && pq.size() == 3) {
            System.out.println("Passed: No elements removed as predicate did not match");
        } else {
            System.out.println("Failed: Unexpected removal occurred");
        }
    }

    static void testRemoveIfOnEmptyQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean removed = pq.removeIf(n -> true);
        if (!removed && pq.isEmpty()) {
            System.out.println("Passed: removeIf on empty queue returns false and queue remains empty");
        } else {
            System.out.println("Failed: Unexpected behavior on empty queue");
        }
    }
}
