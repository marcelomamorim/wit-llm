/*
 * @test    @summary Test the take() method of ArrayBlockingQueue for correct blocking and retrieval behavior.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeBlocksAndRetrieves();
    }

    public static void testTakeBlocksAndRetrieves() throws Exception {
        final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        final Thread consumerThread = new Thread(() -> {
            try {
                Integer value = queue.take();
                if (value != null && value == 42) {
                    System.out.println("Test passed: take() retrieved the correct value.");
                } else {
                    System.out.println("Test failed: take() retrieved incorrect value.");
                }
            } catch (InterruptedException e) {
                System.out.println("Test failed: take() was interrupted.");
            }
        });
        consumerThread.start();
        // Sleep briefly to ensure consumer is blocking on take()
        Thread.sleep(100);
        // Now add an element to unblock take()
        queue.put(42);
        consumerThread.join();
    }
}
