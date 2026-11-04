/* @test    @summary Test for ReentrantReadWriteLock unlock method behavior    @run main ReentrantReadWriteLockWitupTest */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testUnlockWithoutLock();
        testUnlockAfterLock();
    }

    private static void testUnlockWithoutLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            lock.unlock();
            System.out.println("testUnlockWithoutLock: Failed - no exception thrown");
        } catch (IllegalMonitorStateException e) {
            System.out.println("testUnlockWithoutLock: Passed");
        } catch (Throwable t) {
            System.out.println("testUnlockWithoutLock: Failed - unexpected exception: " + t);
        }
    }

    private static void testUnlockAfterLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        try {
            lock.unlock();
            if (lock.getReadHoldCount() == 0) {
                System.out.println("testUnlockAfterLock: Passed");
            } else {
                System.out.println("testUnlockAfterLock: Failed - lock still held");
            }
        } catch (Throwable t) {
            System.out.println("testUnlockAfterLock: Failed - exception: " + t);
        }
    }
}
