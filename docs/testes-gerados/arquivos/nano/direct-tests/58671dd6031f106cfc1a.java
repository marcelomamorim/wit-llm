/* @test    @summary Test ReentrantLock unlock method for correct behavior    @run main ReentrantLockWitupTest */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    public static void main(String[] args) {
        testUnlockReleasesLock();
        testUnlockWithoutLock();
    }

    private static void testUnlockReleasesLock() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            if (!lock.isHeldByCurrentThread()) {
                throw new AssertionError("Lock should be held by current thread after lock() call");
            }
            lock.unlock();
            if (lock.isHeldByCurrentThread()) {
                throw new AssertionError("Lock should not be held after unlock() call");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during testUnlockReleasesLock", e);
        }
    }

    private static void testUnlockWithoutLock() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.unlock();
            throw new AssertionError("Expected IllegalMonitorStateException when unlocking without holding the lock");
        } catch (IllegalMonitorStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type when unlocking without lock", e);
        }
    }
}
