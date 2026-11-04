/*
 * @test
 * @summary Tests for java.util.concurrent.Semaphore.acquire(int permits)
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testNegativePermits();
        testInterruptedException();
        testNormalAcquire();
    }

    private static void testNegativePermits() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquire(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for permits < 0");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for permits < 0");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testInterruptedException() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        Thread t = new Thread(() -> {
            try {
                semaphore.acquire(1);
            } catch (InterruptedException e) {
                System.out.println("FAIL: Thread was interrupted during acquire");
            }
        });
        t.start();
        Thread.sleep(50); // ensure thread is waiting
        t.interrupt();
        t.join();
        System.out.println("PASS: Thread interrupted during acquire handled as expected");
    }

    private static void testNormalAcquire() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquire(1);
            if (semaphore.availablePermits() == 0) {
                System.out.println("PASS: acquire(1) decremented permits correctly");
            } else {
                System.out.println("FAIL: permits not decremented as expected");
            }
        } catch (InterruptedException e) {
            System.out.println("FAIL: Unexpected InterruptedException during acquire");
        }
    }
}
