/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.release(int) behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1L;

        Sync() {
            super();
        }

        @Override
        protected boolean tryRelease(int arg) {
            // For testing, release only if arg is positive
            if (arg > 0) {
                setState(getState() - arg);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Sync sync = new Sync();

        // Initial state set to 1
        sync.setState(1);

        // Test release with positive arg: should return true and unparkSuccessor called if head.waitStatus != 0
        boolean released = sync.release(1);
        if (!released) {
            throw new RuntimeException("release(1) should return true");
        }

        // Test release with zero arg: tryRelease returns false, release returns false
        boolean notReleased = sync.release(0);
        if (notReleased) {
            throw new RuntimeException("release(0) should return false");
        }

        // Test release with negative arg: tryRelease returns false, release returns false
        boolean negativeRelease = sync.release(-1);
        if (negativeRelease) {
            throw new RuntimeException("release(-1) should return false");
        }

        // Test that unparkSuccessor is called when head != null and head.waitStatus != 0
        // We cannot observe unparkSuccessor directly, but we can simulate by setting head and waitStatus
        // Use reflection to set head and waitStatus for testing
        try {
            java.lang.reflect.Field headField = AbstractQueuedSynchronizer.class.getDeclaredField("head");
            headField.setAccessible(true);
            Object node = new Node();
            java.lang.reflect.Field waitStatusField = Node.class.getDeclaredField("waitStatus");
            waitStatusField.setAccessible(true);
            waitStatusField.setInt(node, -1); // non-zero
            headField.set(sync, node);

            // release with positive arg should call unparkSuccessor (no exception means pass)
            boolean result = sync.release(1);
            if (!result) {
                throw new RuntimeException("release(1) with head.waitStatus != 0 should return true");
            }
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }
    }

    // Minimal Node class to allow reflection setting head
    static class Node {
        volatile int waitStatus = 0;
    }
}
