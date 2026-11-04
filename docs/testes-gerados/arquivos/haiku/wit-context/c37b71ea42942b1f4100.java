/*
 * @test
 * @summary Behavioral tests for Semaphore.acquireUninterruptibly
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws Exception {
        testAcquireConsumesOnePermit();
        testBlocksUntilRelease();
        testPreservesInterruptStatusWhenPermitAvailable();
        testRestoresInterruptWhenInterruptedWhileWaiting();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAcquireConsumesOnePermit() {
        Semaphore s = new Semaphore(2);
        s.acquireUninterruptibly();
        check(s.tryAcquire(), "one permit should remain after acquireUninterruptibly");
        check(!s.tryAcquire(), "no permits should remain");
    }

    static void testBlocksUntilRelease() throws InterruptedException {
        Semaphore s = new Semaphore(0);
        AtomicBoolean done = new AtomicBoolean(false);
        Thread t = new Thread(() -> {
            s.acquireUninterruptibly();
            done.set(true);
        });
        t.start();
        t.join(100);
        check(t.isAlive(), "acquireUninterruptibly should block with zero permits");
        s.release();
        t.join(10000);
        check(!t.isAlive(), "thread should complete after release");
        check(done.get(), "acquire should have returned after release");
    }

    static void testPreservesInterruptStatusWhenPermitAvailable() {
        Semaphore s = new Semaphore(1);
        Thread.currentThread().interrupt();
        s.acquireUninterruptibly();
        check(Thread.interrupted(), "interrupt status should be preserved when no blocking occurs");
        check(!s.tryAcquire(), "the single permit should have been consumed");
    }

    static void testRestoresInterruptWhenInterruptedWhileWaiting() throws InterruptedException {
        Semaphore s = new Semaphore(0);
        AtomicBoolean interruptedAfter = new AtomicBoolean(false);
        Thread t = new Thread(() -> {
            s.acquireUninterruptibly();
            interruptedAfter.set(Thread.currentThread().isInterrupted());
        });
        t.start();
        t.interrupt();
        s.release();
        t.join(10000);
        check(!t.isAlive(), "thread should complete after release despite interrupt");
        check(interruptedAfter.get(), "interrupt status should be restored after uninterruptible acquire");
    }
}
