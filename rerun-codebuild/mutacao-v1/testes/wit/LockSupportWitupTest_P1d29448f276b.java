/* @test    @summary Test getBlocker method for null input and valid thread object    @run main LockSupportWitupTest_P1d29448f276b */

import java.util.concurrent.locks.LockSupport;

public class LockSupportWitupTest_P1d29448f276b {

    public static void main(String[] args) {
        testGetBlockerNull();
        testGetBlockerValidThread();
    }

    private static void testGetBlockerNull() {
        try {
            LockSupport.getBlocker(null);
            throw new AssertionError("Expected NullPointerException for null thread");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }

    private static void testGetBlockerValidThread() {
        Thread thread = new Thread();
        try {
            Object result = LockSupport.getBlocker(thread);
            if (result != null) {
                // The result depends on the thread's state; no specific value expected
                // Just ensure no exception is thrown and result is accessible
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception during getBlocker with valid thread: " + t);
        }
    }
}
