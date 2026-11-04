/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.acquire(int) for argument validation and interrupt behavior
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) throws Exception {
        testAcquireNegativePermits();
        testAcquireInterrupt();
        testAcquireNormal();
    }

    static void testAcquireNegativePermits() {
        Semaphore sem = new Semaphore(1);
        try {
            sem.acquire(-1);
            throw new Error("Expected IllegalArgumentException for negative permits");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (InterruptedException ie) {
            throw new Error("Unexpected InterruptedException", ie);
        }
    }

    static void testAcquireInterrupt() throws Exception {
        final Semaphore sem = new Semaphore(0);
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    sem.acquire(1);
                    throw new Error("Expected InterruptedException not thrown");
                } catch (InterruptedException expected) {
                    // expected
                }
            }
        });
        t.start();
        Thread.sleep(100); // ensure thread is waiting
        t.interrupt();
        t.join();
    }

    static void testAcquireNormal() throws Exception {
        Semaphore sem = new Semaphore(1);
        sem.acquire(1); // should succeed immediately
        sem.release(1);
    }
}
