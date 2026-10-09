/* @test    @summary Test for LockSupport.getBlocker method behavior    @run main LockSupportWitupTest_P2045ccaa18ac */

import java.util.concurrent.locks.LockSupport;

public class LockSupportWitupTest_P2045ccaa18ac {

    public static void main(String[] args) {
        testGetBlockerWithNullThread();
        testGetBlockerWithActiveThread();
    }

    private static void testGetBlockerWithNullThread() {
        boolean exceptionThrown = false;
        try {
            LockSupport.getBlocker(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null Thread");
        }
    }

    private static void testGetBlockerWithActiveThread() {
        Thread thread = new Thread(() -> {
            Object blocker = new Object();
            // This test does not set an actual blocker, so getBlocker should return null
            Object result = LockSupport.getBlocker(Thread.currentThread());
            if (result != null) {
                throw new AssertionError("Expected null blocker for thread with no blocker set");
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Additional check: calling getBlocker on main thread (which has no blocker set)
        Object mainResult = LockSupport.getBlocker(Thread.currentThread());
        if (mainResult != null) {
            throw new AssertionError("Expected null blocker for main thread");
        }
    }
}
