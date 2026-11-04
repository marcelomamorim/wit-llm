/*
 * @test
 * @summary Test LinkedBlockingQueue.Itr.remove() behavior including exception paths
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Iterator;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveWithoutNextThrowsIllegalStateException();
        testRemoveAfterNextThrowsUnsupportedOperationException();
    }

    private static void testRemoveWithoutNextThrowsIllegalStateException() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        Iterator<String> it = queue.iterator();
        try {
            // Directly call remove() without next() to trigger IllegalStateException
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testRemoveAfterNextThrowsUnsupportedOperationException() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        Iterator<String> it = queue.iterator();
        // Call next() to set lastRet
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator unexpectedly empty");
        }
        it.next();
        try {
            // According to WIT, remove() after next() throws UnsupportedOperationException
            it.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
