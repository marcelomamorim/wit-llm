/*
 * @test
 * @summary Test LinkedTransferQueue.Itr.remove() method for IllegalStateException and normal behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Iterator;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextSucceeds();
    }

    static void testRemoveWithoutNextThrows() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("one");
        Iterator<String> it = queue.iterator();
        try {
            // remove() before next() should throw IllegalStateException
            it.remove();
            throw new Error("Expected IllegalStateException not thrown");
        } catch (IllegalStateException success) {
            // expected
        }
    }

    static void testRemoveAfterNextSucceeds() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("one");
        queue.add("two");
        Iterator<String> it = queue.iterator();

        // call next() to set lastRet
        String first = it.next();
        if (!"one".equals(first)) {
            throw new Error("Unexpected first element: " + first);
        }

        // remove() should remove the element just returned by next()
        it.remove();

        // After removal, the queue should no longer contain "one"
        if (queue.contains("one")) {
            throw new Error("Element 'one' was not removed by iterator.remove()");
        }

        // The queue should still contain "two"
        if (!queue.contains("two")) {
            throw new Error("Element 'two' should remain in the queue");
        }

        // Calling remove() again without next() should throw IllegalStateException
        try {
            it.remove();
            throw new Error("Expected IllegalStateException on second remove() call not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
