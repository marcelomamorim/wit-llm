/*
 * @test
 * @summary Checks LockSupport.getBlocker null handling and blocker observation while a thread is parked
 * @run main LockSupportWitupTest
 */

import java.util.concurrent.locks.LockSupport;

public class LockSupportWitupTest {

    private static volatile boolean stop;

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testNullThreadThrowsNpe();
        testNotStartedThreadHasNullBlocker();
        testParkedThreadReportsBlocker();
    }

    static void testNullThreadThrowsNpe() {
        boolean thrown = false;
        try {
            LockSupport.getBlocker(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "getBlocker(null) must throw NullPointerException");
    }

    static void testNotStartedThreadHasNullBlocker() {
        Thread t = new Thread(() -> { });
        check(LockSupport.getBlocker(t) == null, "blocker of a new thread must be null");
    }

    static void testParkedThreadReportsBlocker() throws InterruptedException {
        stop = false;
        final Object blocker = new Object();
        Thread t = new Thread(() -> {
            while (!stop) {
                LockSupport.park(blocker);
            }
        });
        t.setDaemon(true);
        t.start();
        try {
            int attempts = 0;
            while (t.getState() != Thread.State.WAITING && t.getState() != Thread.State.TIMED_WAITING) {
                check(attempts++ < 5000, "thread did not reach a parked state");
                Thread.sleep(1);
            }
            check(LockSupport.getBlocker(t) == blocker, "getBlocker must return the blocker used by park");
        } finally {
            stop = true;
            LockSupport.unpark(t);
            t.join();
        }
        check(LockSupport.getBlocker(t) == null, "blocker must be cleared after park returns");
    }
}
