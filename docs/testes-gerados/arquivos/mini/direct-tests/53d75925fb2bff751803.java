/*
 * @test
 * @summary Test PriorityQueue constructor with Comparator
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.Comparator;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        // Test with null comparator (natural ordering)
        PriorityQueue<Integer> pq1 = new PriorityQueue<Integer>((Comparator<? super Integer>) null);
        pq1.add(10);
        pq1.add(5);
        pq1.add(20);
        if (pq1.peek() == null || pq1.peek() != 5) {
            throw new RuntimeException("Failed: Expected head element 5 with null comparator");
        }

        // Test with explicit comparator (reverse order)
        Comparator<Integer> reverseOrder = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return b.compareTo(a);
            }
        };
        PriorityQueue<Integer> pq2 = new PriorityQueue<Integer>(reverseOrder);
        pq2.add(10);
        pq2.add(5);
        pq2.add(20);
        if (pq2.peek() == null || pq2.peek() != 20) {
            throw new RuntimeException("Failed: Expected head element 20 with reverse comparator");
        }

        // Test that adding null element throws NullPointerException
        boolean npeThrown = false;
        try {
            pq1.add(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Failed: Expected NullPointerException when adding null element");
        }

        System.out.println("All tests passed.");
    }
}
