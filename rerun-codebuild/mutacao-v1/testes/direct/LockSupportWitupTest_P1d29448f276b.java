/* @test    @summary Test getBlocker method of LockSupport for various Thread inputs    @run main LockSupportWitupTest_P1d29448f276b */

import java.util.concurrent.locks.LockSupport;

public class LockSupportWitupTest_P1d29448f276b {

    public static void main(String[] args) {
        testNullThread();
        testNormalThread();
        testThreadWithBlocker();
        System.out.println("All tests passed.");
    }

    private static void testNullThread() {
        try {
            LockSupport.getBlocker(null);
            throw new AssertionError("Expected NullPointerException for null Thread");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testNormalThread() {
        Thread t = new Thread();
        Object result = LockSupport.getBlocker(t);
        if (result != null) {
            throw new AssertionError("Expected null for new Thread without blocker, but got: " + result);
        }
    }

    private static void testThreadWithBlocker() {
        Thread t = new Thread();
        Object blocker = new Object();
        setThreadBlocker(t, blocker);
        Object result = LockSupport.getBlocker(t);
        if (result != blocker) {
            throw new AssertionError("Expected blocker object to be returned, but got: " + result);
        }
    }

    private static void setThreadBlocker(Thread t, Object blocker) {
        try {
            java.lang.reflect.Field field = Thread.class.getDeclaredField("parkBlocker");
            field.setAccessible(true);
            field.set(t, blocker);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set thread blocker via reflection", e);
        }
    }
}
