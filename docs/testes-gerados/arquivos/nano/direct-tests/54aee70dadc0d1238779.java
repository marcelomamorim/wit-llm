/* @test    @summary Test PriorityBlockingQueue constructor with initial capacity    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithInitialCapacity();
    }

    public static void testConstructorWithInitialCapacity() {
        int initialCapacity = 10;
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(initialCapacity);
        assert queue != null : "Queue should be instantiated";
        assert queue.size() == 0 : "Queue should be empty after construction";
        // Add elements and verify size
        queue.add(5);
        queue.add(3);
        queue.add(7);
        assert queue.size() == 3 : "Queue should contain 3 elements";
        // Verify that the smallest element is at the head
        Integer head = queue.peek();
        assert head != null && head == 3 : "Head should be the smallest element (3)";
    }
}
