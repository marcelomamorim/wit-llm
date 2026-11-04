/* @test    @summary Test unlocking with valid write and read stamps in StampedLock    @run main StampedLockWitupTest */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testUnlockWithWriteStamp();
        testUnlockWithReadStamp();
    }

    static void testUnlockWithWriteStamp() {
        StampedLock lock = new StampedLock();
        long writeStamp = lock.writeLock();
        lock.unlock(writeStamp);
        // No exception expected, lock should be released successfully
    }

    static void testUnlockWithReadStamp() {
        StampedLock lock = new StampedLock();
        long readStamp = lock.readLock();
        lock.unlock(readStamp);
        // No exception expected, lock should be released successfully
    }
}
