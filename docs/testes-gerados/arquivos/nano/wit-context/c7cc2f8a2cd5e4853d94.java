/*
 * @test
 * @summary Test for LinkedBlockingQueue.toArray() method, covering normal and exceptional behavior.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testToArrayNormal();
        testToArrayWithLockInterruption();
    }

    private static void testToArrayNormal() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        queue.offer("b");
        Object[] array = queue.toArray();
        boolean condition = array.length == 2 && array[0].equals("a") && array[1].equals("b");
        if (!condition) {
            throw new AssertionError("Unexpected array contents: " + java.util.Arrays.toString(array));
        }
    }

    private static void testToArrayWithLockInterruption() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("x");
        queue.offer("y");
        try {
            // Simulate lock acquisition failure by subclassing and throwing in fullyLock()
            LinkedBlockingQueue<String> faultyQueue = new LinkedBlockingQueue<String>() {
                @Override
                public Object[] toArray() {
                    fullyLock();
                    try {
                        throw new UnsupportedOperationException();
                    } finally {
                        fullyUnlock();
                    }
                }
            };
            faultyQueue.offer("x");
            faultyQueue.offer("y");
            faultyQueue.toArray();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
