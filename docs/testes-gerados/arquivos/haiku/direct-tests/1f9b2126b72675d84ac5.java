/*
 * @test
 * @summary Tests observable behavior of Semaphore.acquireUninterruptibly(int)
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws Exception {
        testAcquireReducesPermits();
        testAcquireZeroPermits();
        testNegativePermitsThrows();
        testInterruptStatusPreservedWhenPermitsAvailable();
        testBlockedAcquireWakesOnRelease();
        testBlockedAcquireWithInterruptPreservesFlag();
        testFairSemaphoreAcquire();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAcquireReducesPermits() {
        Semaphore s = new Semaphore(5);
        s.acquireUninterruptibly(3);
        check(s.availablePermits() == 2, "expected 2 permits left, got " + s.availablePermits());
    }

    static void testAcquireZeroPermits() {
        Semaphore s = new Semaphore(1);
        s.acquireUninterruptibly(0);
        check(s.availablePermits() == 1, "acquire(0) must not change permits");
    }

    static void testNegativePermitsThrows() {
        Semaphore s = new Semaphore(1);
        boolean thrown = false;
        try {
            s.acquireUninterruptibly(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for negative permits");
        check(s.availablePermits() == 1, "failed acquire must not change permits");
    }

    static void testInterruptStatusPreservedWhenPermitsAvailable() {
        Semaphore s = new Semaphore(2);
        Thread.currentThread().interrupt();
        s.acquireUninterruptibly(1);
        boolean flag = Thread.interrupted();
        check(flag, "interrupt status must be preserved");
        check(s.availablePermits() == 1, "expected 1 permit left");
    }

    static void testBlockedAcquireWakesOnRelease() throws InterruptedException {
        Semaphore s = new Semaphore(0);
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignored
            }
            s.release(2);
        });
        t.start();
        s.acquireUninterruptibly(2);
        t.join();
        check(s.availablePermits() == 0, "expected 0 permits after acquiring 2 released permits");
    }

    static void testBlockedAcquireWithInterruptPreservesFlag() throws InterruptedException {
        Semaphore s = new Semaphore(0);
        final Thread main = Thread.currentThread();
        Thread t = new Thread(() -> {
            main.interrupt();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignored
            }
            s.release(1);
        });
        t.start();
        s.acquireUninterruptibly(1);
        boolean flag = Thread.interrupted();
        t.join();
        check(flag, "interrupt status must be restored after uninterruptible acquire");
        check(s.availablePermits() == 0, "expected 0 permits after acquire");
    }

    static void testFairSemaphoreAcquire() {
        Semaphore s = new Semaphore(3, true);
        s.acquireUninterruptibly(3);
        check(s.availablePermits() == 0, "expected 0 permits on fair semaphore");
    }
}
