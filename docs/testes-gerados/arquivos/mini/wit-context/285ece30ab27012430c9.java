/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.isQueued(Thread) for null argument and normal behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testIsQueuedNullThread();
        testIsQueuedReturnsFalseForNonexistentThread();
    }

    private static void testIsQueuedNullThread() {
        AbstractQueuedSynchronizer sync = new AbstractQueuedSynchronizer() {};
        try {
            sync.isQueued(null);
            throw new Error("Expected NullPointerException for null thread argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testIsQueuedReturnsFalseForNonexistentThread() {
        AbstractQueuedSynchronizer sync = new AbstractQueuedSynchronizer() {};
        // Since we cannot enqueue threads without subclass support, test that isQueued returns false for current thread
        boolean result = sync.isQueued(Thread.currentThread());
        if (result) {
            throw new Error("Expected isQueued to return false for thread not in queue");
        }
    }

}
