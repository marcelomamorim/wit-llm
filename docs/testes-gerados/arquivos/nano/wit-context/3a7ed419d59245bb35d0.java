/*
 * @test
 * @summary Test for PriorityQueue.removeIf with null predicate
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.function.Predicate;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfValidPredicate();
    }

    static void testRemoveIfNullPredicate() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        try {
            pq.removeIf(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testRemoveIfValidPredicate() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        boolean result = pq.removeIf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer t) {
                return t % 2 == 0;
            }
        });
        if (result) {
            if (pq.size() == 1 && pq.peek() == 1) {
                System.out.println("PASS: removeIf removed even number as expected");
            } else {
                System.out.println("FAIL: removeIf did not remove expected element");
            }
        } else {
            System.out.println("FAIL: removeIf returned false unexpectedly");
        }
    }
}
