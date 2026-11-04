/*
 * @test
 * @summary Verify LockSupport.getBlocker returns the blocker of a parked thread, null otherwise, and throws NullPointerException for a null thread
 * @run main LockSupportWitupTest
 */
import java.util.concurrent.locks.LockSupport;

public class LockSupportWitupTest {
    static volatile boolean done;

    public static void main(String[] args) throws Exception {
        try {
            LockSupport.getBlocker(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }

        if (LockSupport.getBlocker(Thread.currentThread()) != null) {
            throw new AssertionError("current thread should have no blocker");
        }

        final Object blocker = new Object();
        Thread t = new Thread(() -> {
            while (!done) {
                LockSupport.park(blocker);
            }
        });
        t.start();
        while (t.getState() != Thread.State.WAITING) {
            Thread.yield();
        }
        if (LockSupport.getBlocker(t) != blocker) {
            throw new AssertionError("expected blocker of parked thread");
        }

        done = true;
        LockSupport.unpark(t);
        t.join();
        if (LockSupport.getBlocker(t) != null) {
            throw new AssertionError("blocker should be cleared after park returns");
        }
    }
}
