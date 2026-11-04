/*
 * @test
 * @summary Test getNumberWaiting method of CyclicBarrier
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a CyclicBarrier with 3 parties
        CyclicBarrier barrier = new CyclicBarrier(3);

        // Initially, no threads are waiting, so getNumberWaiting() should be 0
        int initialWaiting = barrier.getNumberWaiting();
        if (initialWaiting != 0) {
            throw new AssertionError("Expected 0 waiting initially, got " + initialWaiting);
        }

        // Create two threads that will wait on the barrier
        Thread t1 = new Thread(new BarrierAwaiter(barrier));
        Thread t2 = new Thread(new BarrierAwaiter(barrier));

        t1.start();
        t2.start();

        // Wait until both threads have started and are waiting
        // We poll getNumberWaiting() until it reaches 2 or timeout
        boolean waitingReached = false;
        for (int i = 0; i < 100; i++) {
            int waiting = barrier.getNumberWaiting();
            if (waiting == 2) {
                waitingReached = true;
                break;
            }
            Thread.sleep(10);
        }

        if (!waitingReached) {
            throw new AssertionError("Expected 2 threads waiting, but did not reach that state");
        }

        // Now release the barrier by starting a third thread that calls await
        Thread t3 = new Thread(new BarrierAwaiter(barrier));
        t3.start();

        // Wait for all threads to finish
        t1.join();
        t2.join();
        t3.join();

        // After barrier is tripped, getNumberWaiting() should be 0 again
        int afterWaiting = barrier.getNumberWaiting();
        if (afterWaiting != 0) {
            throw new AssertionError("Expected 0 waiting after barrier tripped, got " + afterWaiting);
        }
    }

    static class BarrierAwaiter implements Runnable {
        private final CyclicBarrier barrier;

        BarrierAwaiter(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        public void run() {
            try {
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
