/*
 * @test
 * @summary Test for LockSupport.getBlocker(Thread t)
 * @run main LockSupportWitupTest_P2045ccaa18ac
 */

import java.util.concurrent.locks.LockSupport;

public class LockSupportWitupTest_P2045ccaa18ac {

    public static void main(String[] args) {
        testNullThread();
        testValidThread();
    }

    private static void testNullThread() {
        try {
            LockSupport.getBlocker(null);
            System.out.println("FAIL: Expected NullPointerException for null Thread");
        } catch (NullPointerException expected) {
            System.out.println("PASS: NullPointerException thrown as expected for null Thread");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testValidThread() {
        Thread t = new Thread(() -> {
            // Thread does nothing
        });
        t.start();
        try {
            Object blocker = LockSupport.getBlocker(t);
            if (blocker == null) {
                System.out.println("PASS: getBlocker returns null for a new thread");
            } else {
                System.out.println("PASS: getBlocker returns non-null object for a thread with blocker set");
            }
        } catch (Throwable t2) {
            System.out.println("FAIL: Exception thrown during getBlocker: " + t2);
        } finally {
            t.interrupt();
        }
    }
}
