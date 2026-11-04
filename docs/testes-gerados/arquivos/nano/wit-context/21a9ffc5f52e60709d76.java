/*
 * @test
 * @summary Test for CyclicBarrier.getNumberWaiting() method.
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicBarrierWitupTest {

    public static void main(String[] args) throws Exception {
        testGetNumberWaitingInitial();
        testGetNumberWaitingAfterAwait();
    }

    private static void testGetNumberWaitingInitial() {
        CyclicBarrier barrier = new CyclicBarrier(2);
        int waiting = barrier.getNumberWaiting();
        if (waiting != 0) {
            throw new AssertionError("Expected initial getNumberWaiting() to be 0, but was " + waiting);
        }
    }

    private static void testGetNumberWaitingAfterAwait() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(2);
        Thread t = new Thread(() -> {
            try {
                barrier.await();
            } catch (Exception e) {
                // ignore
            }
        });
        t.start();
        // Wait briefly to ensure thread has called await()
        Thread.sleep(50);
        int waiting = barrier.getNumberWaiting();
        if (waiting != 1) {
            throw new AssertionError("Expected getNumberWaiting() to be 1 after one thread awaits, but was " + waiting);
        }
        t.join();
        // After barrier is tripped, getNumberWaiting should be 0
        int waitingAfter = barrier.getNumberWaiting();
        if (waitingAfter != 0) {
            throw new AssertionError("Expected getNumberWaiting() to be 0 after barrier tripped, but was " + waitingAfter);
        }
    }
}
