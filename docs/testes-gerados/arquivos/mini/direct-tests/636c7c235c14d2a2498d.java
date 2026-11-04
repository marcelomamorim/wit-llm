/*
 * @test
 * @summary Test java.util.concurrent.CyclicBarrier.isBroken() method
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a CyclicBarrier with 1 party, no barrier action
        CyclicBarrier barrier = new CyclicBarrier(1);

        // Initially, barrier should not be broken
        if (barrier.isBroken()) {
            throw new AssertionError("Barrier should not be broken initially");
        }

        // Await once to trip the barrier and reset it
        barrier.await();

        // After tripping, barrier should not be broken
        if (barrier.isBroken()) {
            throw new AssertionError("Barrier should not be broken after successful await");
        }

        // Create a barrier with 2 parties
        final CyclicBarrier barrier2 = new CyclicBarrier(2);

        // Start a thread that will await and then be interrupted to break the barrier
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    barrier2.await();
                } catch (Exception e) {
                    // ignore
                }
            }
        });
        t.start();

        // Interrupt the thread to break the barrier
        t.interrupt();

        // Wait for thread to finish
        t.join();

        // Now the barrier should be broken
        if (!barrier2.isBroken()) {
            throw new AssertionError("Barrier should be broken after interruption");
        }
    }
}
