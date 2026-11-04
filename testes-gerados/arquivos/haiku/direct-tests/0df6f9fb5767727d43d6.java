/*
 * @test
 * @summary StampedLock.newCondition() must throw UnsupportedOperationException and leave the lock usable
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {
    public static void main(String[] args) {
        StampedLock lock = new StampedLock();

        for (int i = 0; i < 3; i++) {
            boolean thrown = false;
            try {
                lock.newCondition();
            } catch (UnsupportedOperationException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected UnsupportedOperationException on call " + i);
            }
        }

        long stamp = lock.writeLock();
        if (stamp == 0L) {
            throw new RuntimeException("writeLock returned invalid stamp after newCondition failures");
        }
        lock.unlockWrite(stamp);

        long readStamp = lock.readLock();
        if (readStamp == 0L) {
            throw new RuntimeException("readLock returned invalid stamp after newCondition failures");
        }
        lock.unlockRead(readStamp);
    }
}
