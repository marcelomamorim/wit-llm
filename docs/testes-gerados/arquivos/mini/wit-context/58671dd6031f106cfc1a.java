/*
 * @test
 * @summary Test ReentrantLock.unlock() behavior including exception path
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {
    public static void main(String[] args) {
        testUnlockThrowsIfNotHeld();
        testUnlockAfterLockSucceeds();
    }

    private static void testUnlockThrowsIfNotHeld() {
        final ReentrantLock lock = new ReentrantLock();
        // According to WIT expath, unlock() throws UnsupportedOperationException if called without lock held
        // But in actual JDK 11, unlock() throws IllegalMonitorStateException if current thread does not hold the lock
        // So we test for IllegalMonitorStateException here to match actual behavior
        boolean threwExpected = false;
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException ex) {
            threwExpected = true;
        } catch (UnsupportedOperationException ex) {
            // fallback if implementation differs
            threwExpected = true;
        } catch (RuntimeException ex) {
            // unexpected exception
            throw new RuntimeException("Unexpected exception from unlock(): " + ex, ex);
        }
        if (!threwExpected) {
            throw new RuntimeException("unlock() did not throw expected exception when lock not held");
        }
    }

    private static void testUnlockAfterLockSucceeds() {
        final ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            // unlock should succeed without exception when lock is held by current thread
            lock.unlock();
        } catch (RuntimeException ex) {
            throw new RuntimeException("unlock() threw unexpected exception when lock held", ex);
        }
    }
}
