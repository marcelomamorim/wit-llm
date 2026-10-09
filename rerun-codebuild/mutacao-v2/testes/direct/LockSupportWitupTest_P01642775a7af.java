/*
 * @test
 * @summary Test LockSupport.getBlocker(Thread) behavior
 * @run main LockSupportWitupTest_P01642775a7af
 */

import java.util.concurrent.locks.LockSupport;

public class LockSupportWitupTest_P01642775a7af {
    public static void main(String[] args) {
        testGetBlockerWithNullThread();
        testGetBlockerWithCurrentThread();
    }

    private static void testGetBlockerWithNullThread() {
        try {
            LockSupport.getBlocker(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetBlockerWithCurrentThread() {
        Thread current = Thread.currentThread();
        Object blocker = LockSupport.getBlocker(current);
        if (blocker != null) {
            throw new Error("Expected null blocker for current thread but got: " + blocker);
        }
    }
}
