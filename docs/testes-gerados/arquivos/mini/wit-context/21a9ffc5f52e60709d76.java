/*
 * @test
 * @summary Test deterministic behavior of CyclicBarrier.getNumberWaiting()
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) throws Exception {
        testGetNumberWaitingBasic();
    }

    private static void testGetNumberWaitingBasic() {
        final int parties = 3;
        CyclicBarrier barrier = new CyclicBarrier(parties);

        // Initially, no threads are waiting, so getNumberWaiting() should return 0
        int waiting = barrier.getNumberWaiting();
        if (waiting != 0) {
            throw new AssertionError("Expected 0 waiting threads initially, but got " + waiting);
        }

        // Create and start one thread that waits at the barrier
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {
                    // Ignore exceptions for this test
                }
            }
        });
        t1.start();

        // Wait briefly to ensure the thread reaches await()
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Now getNumberWaiting() should be 1
        waiting = barrier.getNumberWaiting();
        if (waiting != 1) {
            throw new AssertionError("Expected 1 waiting thread after one await, but got " + waiting);
        }

        // Cleanup: release the barrier by starting two more threads
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {
                    // Ignore exceptions
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {
                    // Ignore exceptions
                }
            }
        });
        t2.start();
        t3.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // After barrier is tripped, getNumberWaiting() should be 0 again
        waiting = barrier.getNumberWaiting();
        if (waiting != 0) {
            throw new AssertionError("Expected 0 waiting threads after barrier tripped, but got " + waiting);
        }
    }
}
