/*
 * @test
 * @summary Test PriorityBlockingQueue.Itr.next() method for NoSuchElementException and normal behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.Iterator;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testNextThrowsNoSuchElementException();
        testNextReturnsElementsInIteration();
    }

    static void testNextThrowsNoSuchElementException() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        Iterator<Integer> it = pq.iterator();
        // Iterator on empty queue should throw NoSuchElementException on next()
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }

    static void testNextReturnsElementsInIteration() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(10);
        pq.add(20);
        pq.add(15);

        Iterator<Integer> it = pq.iterator();
        int count = 0;
        while (it.hasNext()) {
            Integer val = it.next();
            if (val == null) {
                throw new AssertionError("Iterator.next() returned null element");
            }
            count++;
        }
        if (count != 3) {
            throw new AssertionError("Iterator did not iterate over all elements");
        }
    }
}
