/*
 * @test
 * @summary Test deterministic behavior of CyclicBarrier.isBroken()
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

        // Interrupt a thread waiting on the barrier to cause broken state
        final Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    barrier.await();
                    throw new AssertionError("Expected InterruptedException");
                } catch (InterruptedException ie) {
                    // Expected
                } catch (Exception e) {
                    throw new AssertionError("Unexpected exception: " + e);
                }
            }
        });

        t.start();
        Thread.sleep(100); // Give thread time to start and wait
        t.interrupt();
        t.join();

        // Now barrier should be broken
        if (!barrier.isBroken()) {
            throw new AssertionError("Barrier should be broken after interrupted await");
        }

        // Reset the barrier to clear broken state
        barrier.reset();

        // After reset, barrier should not be broken
        if (barrier.isBroken()) {
            throw new AssertionError("Barrier should not be broken after reset");
        }
    }
}
