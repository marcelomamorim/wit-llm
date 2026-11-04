/*
 * @test
 * @summary Test LinkedBlockingQueue remove() method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;
import java.lang.IllegalStateException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveWithoutNext();
    }

    // Test that remove() throws IllegalStateException if called before next()
    private static void testRemoveWithoutNext() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        java.util.Iterator<String> it = queue.iterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
