/*
 * @test
 * @summary Test LinkedBlockingQueue.iterator() returns an Iterator and supports remove
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");

        Iterator<String> it = queue.iterator();
        if (it == null) {
            throw new AssertionError("iterator() returned null");
        }

        // The iterator should return elements in FIFO order
        if (!it.hasNext()) {
            throw new AssertionError("iterator() hasNext() returned false on non-empty queue");
        }

        String first = it.next();
        if (!"one".equals(first)) {
            throw new AssertionError("iterator() next() returned wrong element: " + first);
        }

        // Test remove() supported by iterator
        it.remove();

        // After removal, the queue should no longer contain "one"
        if (queue.contains("one")) {
            throw new AssertionError("Element 'one' was not removed by iterator.remove()");
        }

        // The next element should be "two"
        if (!it.hasNext()) {
            throw new AssertionError("iterator() hasNext() returned false before last element");
        }

        String second = it.next();
        if (!"two".equals(second)) {
            throw new AssertionError("iterator() next() returned wrong element: " + second);
        }

        // No more elements
        if (it.hasNext()) {
            throw new AssertionError("iterator() hasNext() returned true after last element");
        }

        // Test that calling remove() twice without next() throws IllegalStateException
        boolean threw = false;
        try {
            it.remove(); // valid remove after next()
            it.remove(); // invalid remove, should throw
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("iterator.remove() did not throw IllegalStateException when called twice without next()");
        }
    }
}
