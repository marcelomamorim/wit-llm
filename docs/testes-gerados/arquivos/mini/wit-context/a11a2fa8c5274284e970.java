/*
 * @test
 * @summary Test ArrayBlockingQueue.Itr.hasNext() behavior including exception path
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testHasNextTrue();
        testHasNextFalseThrows();
    }

    static void testHasNextTrue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        Iterator<Integer> it = queue.iterator();
        // The iterator should have next initially
        if (!it.hasNext()) {
            throw new AssertionError("Expected hasNext() to return true when nextItem is present");
        }
    }

    static void testHasNextFalseThrows() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        Iterator<Integer> it = queue.iterator();
        // The iterator is empty, so hasNext() should return false after noNext() is called
        // According to WIT, noNext() throws UnsupportedOperationException if nextItem == null
        // But in current code, hasNext() calls noNext() and returns false
        // We test that hasNext() returns false and does not throw
        boolean thrown = false;
        try {
            boolean result = it.hasNext();
            if (result) {
                throw new AssertionError("Expected hasNext() to return false when iterator is empty");
            }
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (thrown) {
            throw new AssertionError("hasNext() threw UnsupportedOperationException unexpectedly");
        }
    }
}
