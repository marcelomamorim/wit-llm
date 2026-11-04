/*
 * @test
 * @summary Verifies public Thread.join() returns immediately for unstarted threads, waits for termination, and propagates InterruptedException.
 * @run main ThreadWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadWitupTest {

    public static void main(String[] args) throws Exception {
        testJoinOnUnstartedThreadReturnsImmediately();
        testJoinWaitsForTermination();
        testJoinInterruptedThrowsAndClearsFlag();
    }

    static void testJoinOnUnstartedThreadReturnsImmediately() throws Exception {
        Thread t = new Thread(() -> { });
        t.join();
        if (t.isAlive()) {
            throw new RuntimeException("unstarted thread reported alive after join()");
        }
    }

    static void testJoinWaitsForTermination() throws Exception {
        final CountDownLatch release = new CountDownLatch(1);
        final AtomicBoolean finished = new AtomicBoolean(false);
        Thread t = new Thread(() -> {
            try {
                release.await();
            } catch (InterruptedException e) {
                return;
            }
            finished.set(true);
        });
        t.start();
        release.countDown();
        t.join();
        if (t.isAlive()) {
            throw new RuntimeException("thread still alive after join()");
        }
        if (!finished.get()) {
            throw new RuntimeException("join() returned before run() completed");
        }
    }

    static void testJoinInterruptedThrowsAndClearsFlag() throws Exception {
        final CountDownLatch never = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            try {
                never.await();
            } catch (InterruptedException e) {
                // ignore
            }
        });
        t.start();
        try {
            Thread.currentThread().interrupt();
            try {
                t.join();
                throw new RuntimeException("join() did not throw InterruptedException");
            } catch (InterruptedException expected) {
                // expected
            }
            if (Thread.currentThread().isInterrupted()) {
                throw new RuntimeException("interrupt status not cleared after InterruptedException");
            }
        } finally {
            Thread.interrupted();
            never.countDown();
            t.join();
        }
    }
}
