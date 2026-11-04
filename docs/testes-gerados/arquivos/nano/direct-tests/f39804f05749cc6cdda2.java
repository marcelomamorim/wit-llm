/*
 * @test
 * @summary Test Thread.join() method for basic behavior
 * @run main ThreadWitupTest
 */

import java.lang.Thread;

public class ThreadWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testJoinWithRunningThread();
        testJoinWithCompletedThread();
        testJoinWithInterruptedThread();
    }

    private static void testJoinWithRunningThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // ignore
            }
        });
        t.start();
        t.join();
        assert !t.isAlive() : "Thread should have finished after join";
    }

    private static void testJoinWithCompletedThread() throws InterruptedException {
        Thread t = new Thread(() -> {});
        t.start();
        t.join();
        assert !t.isAlive() : "Thread should be dead after join";
    }

    private static void testJoinWithInterruptedThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignore
            }
        });
        t.start();
        t.interrupt();
        try {
            t.join();
        } catch (InterruptedException e) {
            // expected if main thread is interrupted
        }
        assert !t.isAlive() : "Thread should have finished after join";
    }
}
