/* @test    @summary Test the remove() method of ArrayBlockingQueue for correct removal behavior    @run main ArrayBlockingQueueRemoveTest */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueRemoveTest {

    public static void main(String[] args) {
        testRemoveFromFullQueue();
        testRemoveFromEmptyQueue();
        testRemoveAfterAdd();
    }

    static void testRemoveFromFullQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        queue.add(2);
        queue.remove();
        assert queue.size() == 1 : "Queue size should be 1 after one remove";
        assert queue.peek() == 2 : "Remaining element should be 2";
    }

    static void testRemoveFromEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        boolean exceptionThrown = false;
        try {
            queue.remove();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "remove() on empty queue should throw NoSuchElementException";
    }

    static void testRemoveAfterAdd() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(10);
        queue.add(20);
        queue.remove();
        assert queue.peek() == 20 : "After removal, head should be 20";
        assert queue.size() == 1 : "Queue size should be 1";
    }
}
