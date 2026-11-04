/*
 * @test
 * @summary Tests observable behavior of Semaphore.acquireUninterruptibly()
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testDecrementsPermits() {
        Semaphore s = new Semaphore(2);
        s.acquireUninterruptibly();
        check(s.availablePermits() == 1, "expected 1 permit after one acquire");
        s.acquireUninterruptibly();
        check(s.availablePermits() == 0, "expected 0 permits after two acquires");
    }

    static void testFairSemaphore() {
        Semaphore s = new Semaphore(3, true);
        s.acquireUninterruptibly();
        check(s.availablePermits() == 2, "fair semaphore: expected 2 permits");
    }

    static void testReleaseThenAcquire() {
        Semaphore s = new Semaphore(0);
        s.release();
        s.acquireUninterruptibly();
        check(s.availablePermits() == 0, "expected 0 permits after release and acquire");
    }

    static void testInterruptFlagPreservedWhenPermitAvailable() {
        Semaphore s = new Semaphore(1);
        Thread.currentThread().interrupt();
        try {
            s.acquireUninterruptibly();
            check(Thread.currentThread().isInterrupted(),
                  "interrupt status should be preserved");
            check(s.availablePermits() == 0, "expected 0 permits");
        } finally {
            Thread.interrupted();
        }
    }

    static void testBlocksUntilReleaseDespiteInterrupt() throws InterruptedException {
        final Semaphore s = new Semaphore(0);
        final boolean[] done = new boolean[1];
        Thread t = new Thread(() -> {
            s.acquireUninterruptibly();
            synchronized (done) {
                done[0] = true;
            }
        });
        t.start();
        t.interrupt();
        t.join(100);
        check(t.isAlive(), "thread must not finish without a permit");
        s.release();
        t.join(10000);
        check(!t.isAlive(), "thread should finish after release");
        synchronized (done) {
            check(done[0], "acquireUninterruptibly should have returned");
        }
        check(s.availablePermits() == 0, "expected 0 permits after handoff");
    }

    public static void main(String[] args) throws Exception {
        testDecrementsPermits();
        testFairSemaphore();
        testReleaseThenAcquire();
        testInterruptFlagPreservedWhenPermitAvailable();
        testBlocksUntilReleaseDespiteInterrupt();
    }
}
