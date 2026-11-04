/*
 * @test
 * @summary Test PriorityQueue(Comparator<? super E>) constructor behavior
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.Comparator;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullComparator();
        testConstructorWithCustomComparator();
    }

    private static void testConstructorWithNullComparator() {
        // Should create a PriorityQueue with natural ordering (null comparator)
        PriorityQueue<Integer> pq = new PriorityQueue<>(null);
        pq.add(10);
        pq.add(5);
        pq.add(20);
        // The head should be the least element (5)
        if (pq.peek() == null || pq.peek() != 5) {
            throw new AssertionError("Expected head element 5 but got " + pq.peek());
        }
    }

    private static void testConstructorWithCustomComparator() {
        // Comparator that reverses natural order
        Comparator<Integer> reverseComparator = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return b.compareTo(a);
            }
        };

        PriorityQueue<Integer> pq = new PriorityQueue<>(reverseComparator);
        pq.add(10);
        pq.add(5);
        pq.add(20);
        // The head should be the greatest element (20) due to reverse comparator
        if (pq.peek() == null || pq.peek() != 20) {
            throw new AssertionError("Expected head element 20 but got " + pq.peek());
        }
    }
}
