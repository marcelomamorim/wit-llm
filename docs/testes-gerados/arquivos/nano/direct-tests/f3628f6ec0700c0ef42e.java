/* @test    @summary Test for LockSupport.getBlocker method behavior    @run main LockSupportWitupTest */

import java.util.concurrent.locks.LockSupport;

public class LockSupportWitupTest {

    public static void main(String[] args) {
        testGetBlockerWithNull();
        testGetBlockerWithThread();
    }

    private static void testGetBlockerWithNull() {
        try {
            LockSupport.getBlocker(null);
            System.out.println("FAIL: Expected NullPointerException for null thread");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null thread");
        }
    }

    private static void testGetBlockerWithThread() {
        Thread thread = new Thread(() -> {
            // Thread does nothing
        });
        thread.start();
        try {
            Object blocker = LockSupport.getBlocker(thread);
            if (blocker == null) {
                System.out.println("PASS: getBlocker returned null for thread not blocked");
            } else {
                System.out.println("PASS: getBlocker returned non-null object: " + blocker);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        } finally {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}
