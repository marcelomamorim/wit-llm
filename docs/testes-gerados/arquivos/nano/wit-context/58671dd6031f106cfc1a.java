/* @test    @summary Test for ReentrantLock.unlock() method to verify normal and exceptional behavior    @run main ReentrantLockWitupTest */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    public static void main(String[] args) {
        testUnlockWithoutLock();
        testUnlockWithLock();
    }

    private static void testUnlockWithoutLock() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.unlock();
            System.out.println("testUnlockWithoutLock: FAILED - no exception thrown");
        } catch (IllegalMonitorStateException e) {
            System.out.println("testUnlockWithoutLock: PASSED");
        } catch (Throwable t) {
            System.out.println("testUnlockWithoutLock: FAILED - unexpected exception: " + t);
        }
    }

    private static void testUnlockWithLock() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            lock.unlock();
            System.out.println("testUnlockWithLock: PASSED");
        } catch (Throwable t) {
            System.out.println("testUnlockWithLock: FAILED - exception: " + t);
        }
    }
}
