/*
 * @test
 * @summary Test ArrayBlockingQueue iterator basic behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a queue with capacity 3
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        // Initially empty queue iterator should have no elements
        Iterator<String> it = queue.iterator();
        if (it.hasNext()) {
            throw new RuntimeException("Iterator should have no elements on empty queue");
        }

        // Add one element and test iterator
        queue.add("one");
        it = queue.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have elements after add");
        }
        String val = it.next();
        if (!"one".equals(val)) {
            throw new RuntimeException("Iterator returned wrong element: " + val);
        }
        if (it.hasNext()) {
            throw new RuntimeException("Iterator should have only one element");
        }

        // Add two more elements
        queue.add("two");
        queue.add("three");

        // Iterate over all elements and check order
        it = queue.iterator();
        String[] expected = {"one", "two", "three"};
        int index = 0;
        while (it.hasNext()) {
            String e = it.next();
            if (!expected[index].equals(e)) {
                throw new RuntimeException("Iterator element mismatch at index " + index + ": expected " + expected[index] + ", got " + e);
            }
            index++;
        }
        if (index != expected.length) {
            throw new RuntimeException("Iterator did not return all elements");
        }

        // Test that next() on exhausted iterator throws NoSuchElementException
        boolean threw = false;
        try {
            it.next();
        } catch (java.util.NoSuchElementException ex) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Iterator.next() should throw NoSuchElementException when exhausted");
        }
    }
}
