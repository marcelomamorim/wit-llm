/* @test    @summary Test PriorityQueue constructor with initial capacity    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithInitialCapacity();
    }

    public static void testConstructorWithInitialCapacity() {
        int capacity = 10;
        PriorityQueue<Integer> pq = new PriorityQueue<>(capacity);
        assert pq != null : "PriorityQueue instance should not be null";
        assert pq.size() == 0 : "PriorityQueue should be empty upon creation";
        // Verify that the capacity is at least the initial capacity (not directly accessible, so test by adding elements)
        for (int i = 0; i < capacity; i++) {
            pq.offer(i);
        }
        assert pq.size() == capacity : "PriorityQueue should contain " + capacity + " elements";
        // Remove elements to verify order
        Integer prev = null;
        while (!pq.isEmpty()) {
            Integer current = pq.poll();
            if (prev != null) {
                assert prev <= current : "Elements should be in non-decreasing order";
            }
            prev = current;
        }
    }
}
