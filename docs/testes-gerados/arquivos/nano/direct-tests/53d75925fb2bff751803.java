/* @test    @summary Test PriorityQueue constructor with Comparator parameter    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;
import java.util.Comparator;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithComparator();
    }

    public static void testConstructorWithComparator() {
        Comparator<Integer> comparator = Integer::compare;
        PriorityQueue<Integer> pq = new PriorityQueue<>(comparator);
        if (pq == null) {
            throw new AssertionError("PriorityQueue instance should not be null");
        }
        if (pq.size() != 0) {
            throw new AssertionError("PriorityQueue should be initialized empty");
        }
        Integer first = pq.peek();
        if (first != null) {
            throw new AssertionError("Peek on empty PriorityQueue should return null");
        }
        pq.offer(10);
        pq.offer(5);
        Integer min = pq.peek();
        if (min == null || min != 5) {
            throw new AssertionError("PriorityQueue should return smallest element at peek");
        }
    }
}
