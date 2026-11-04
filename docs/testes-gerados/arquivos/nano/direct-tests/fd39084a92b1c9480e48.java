/* @test    @summary Test ArrayBlockingQueue.remove(Object o) method for correct removal behavior and edge cases    @run main ArrayBlockingQueueWitupTest */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNonExistingElement();
        testRemoveNullElement();
        testRemoveFromEmptyQueue();
    }

    static void testRemoveExistingElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        boolean removed = queue.remove("b");
        assert removed : "Failed to remove existing element";
        assert queue.size() == 2 : "Queue size should be 2 after removal";
        assert !queue.contains("b") : "Queue should not contain removed element";
    }

    static void testRemoveNonExistingElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.add("x");
        queue.add("y");
        boolean removed = queue.remove("z");
        assert !removed : "Removing non-existing element should return false";
        assert queue.size() == 2 : "Queue size should remain 2";
    }

    static void testRemoveNullElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.add("foo");
        boolean removed = queue.remove(null);
        assert !removed : "Removing null should return false";
        assert queue.size() == 1 : "Queue size should remain 1";
    }

    static void testRemoveFromEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        boolean removed = queue.remove("any");
        assert !removed : "Removing from empty queue should return false";
        assert queue.isEmpty() : "Queue should be empty";
    }
}
