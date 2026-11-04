/*
 * @test
 * @summary Test ConcurrentLinkedQueue.Itr.remove() method for IllegalStateException and normal behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Iterator;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextClearsItem();
    }

    private static void testRemoveWithoutNextThrows() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        Iterator<String> it = queue.iterator();
        try {
            it.remove();
            throw new Error("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testRemoveAfterNextClearsItem() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        Iterator<String> it = queue.iterator();
        if (!it.hasNext()) {
            throw new Error("Iterator should have next element");
        }
        String val = it.next();
        if (!"a".equals(val)) {
            throw new Error("Iterator next returned unexpected value: " + val);
        }
        it.remove();
        // After remove, the element should be logically removed (item set to null internally)
        // The queue should now be empty
        if (!queue.isEmpty()) {
            throw new Error("Queue should be empty after remove");
        }
        // Subsequent remove without next should throw
        try {
            it.remove();
            throw new Error("Expected IllegalStateException not thrown on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
