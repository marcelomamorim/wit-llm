/*
 * @test    @summary Test for ArrayBlockingQueue.next() method behavior
 * @run main ArrayBlockingQueueNextMethodTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueNextMethodTest {

    public static void main(String[] args) {
        testNextOnEmptyQueue();
        testNextOnPopulatedQueue();
        testNextAfterIteration();
    }

    static void testNextOnEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        try {
            // Attempt to call next() on an empty queue should throw NoSuchElementException
            ArrayBlockingQueue<String>.Itr iterator = queue.new Itr();
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    static void testNextOnPopulatedQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("element1");
        queue.add("element2");
        ArrayBlockingQueue<String>.Itr iterator = queue.new Itr();
        String first = iterator.next();
        if (!"element1".equals(first)) {
            throw new AssertionError("Expected 'element1', got: " + first);
        }
        String second = iterator.next();
        if (!"element2".equals(second)) {
            throw new AssertionError("Expected 'element2', got: " + second);
        }
    }

    static void testNextAfterIteration() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("element1");
        ArrayBlockingQueue<String>.Itr iterator = queue.new Itr();
        iterator.next();
        // Remove the element from the queue to simulate change
        queue.clear();
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException not thrown after queue cleared");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
