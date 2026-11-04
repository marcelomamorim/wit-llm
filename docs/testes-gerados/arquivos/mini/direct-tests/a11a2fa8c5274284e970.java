/*
 * @test
 * @summary Test ArrayBlockingQueue iterator hasNext method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Iterator;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testHasNextTrue();
        testHasNextFalse();
    }

    static void testHasNextTrue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.add("one");
        Iterator<String> it = queue.iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Expected hasNext() to return true when element is present");
        }
    }

    static void testHasNextFalse() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        Iterator<String> it = queue.iterator();
        if (it.hasNext()) {
            throw new AssertionError("Expected hasNext() to return false when queue is empty");
        }
    }
}
