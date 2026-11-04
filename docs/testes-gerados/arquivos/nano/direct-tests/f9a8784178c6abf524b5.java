/* @test    @summary Test retainAll method of PriorityQueue for correct behavior    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Collection;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithEmptyCollection();
        testRetainAllWithSubset();
        testRetainAllWithNoOverlap();
        testRetainAllWithFullOverlap();
    }

    static void testRetainAllWithEmptyCollection() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        boolean changed = pq.retainAll(Arrays.asList());
        assert !changed : "Expected no change when retaining with empty collection";
        assert pq.size() == 0 : "Expected queue to be empty after retainAll with empty collection";
    }

    static void testRetainAllWithSubset() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        boolean changed = pq.retainAll(Arrays.asList(2, 3));
        assert changed : "Expected change when retaining subset";
        assert pq.size() == 2 : "Expected size 2 after retainAll";
        assert pq.contains(2) : "Queue should contain 2";
        assert pq.contains(3) : "Queue should contain 3";
    }

    static void testRetainAllWithNoOverlap() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        boolean changed = pq.retainAll(Arrays.asList(4, 5));
        assert changed : "Expected change when no elements are retained";
        assert pq.size() == 0 : "Expected queue to be empty after retainAll with no overlap";
    }

    static void testRetainAllWithFullOverlap() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        boolean changed = pq.retainAll(Arrays.asList(1, 2, 3));
        assert !changed : "Expected no change when retaining all elements";
        assert pq.size() == 3 : "Expected size 3 after retainAll";
        assert pq.contains(1) : "Queue should contain 1";
        assert pq.contains(2) : "Queue should contain 2";
        assert pq.contains(3) : "Queue should contain 3";
    }
}
