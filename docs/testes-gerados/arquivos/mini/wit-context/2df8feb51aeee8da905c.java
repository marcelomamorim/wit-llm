/*
 * @test
 * @summary Test ArrayBlockingQueue.Itr.remove() throws UnsupportedOperationException
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testItrRemoveUnsupported();
        testItrRemoveIllegalState();
        testItrRemoveAfterRemoveAt();
    }

    private static void testItrRemoveUnsupported() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        queue.add(2);
        Iterator<Integer> it = queue.iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        it.next();
        try {
            it.remove();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }

    private static void testItrRemoveIllegalState() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        Iterator<Integer> it = queue.iterator();
        try {
            it.remove(); // remove called before next
            throw new AssertionError("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // Expected exception
        } catch (UnsupportedOperationException e) {
            // Some implementations may throw UnsupportedOperationException instead
        }
    }

    private static void testItrRemoveAfterRemoveAt() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        queue.add(2);
        Iterator<Integer> it = queue.iterator();
        it.next();
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            // Expected, ignore
        }
        try {
            it.remove(); // second remove call should throw IllegalStateException or UnsupportedOperationException
            throw new AssertionError("Expected IllegalStateException or UnsupportedOperationException on second remove");
        } catch (IllegalStateException | UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
