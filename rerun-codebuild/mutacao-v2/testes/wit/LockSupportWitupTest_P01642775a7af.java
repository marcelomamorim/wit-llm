/*
 * @test
 * @summary Test LockSupport.getBlocker(Thread) for null argument and normal usage
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
        // The blocker may be null or non-null depending on park state, but must not throw
        if (blocker != null && !(blocker instanceof Object)) {
            throw new Error("Blocker returned is not an Object");
        }
    }
}
