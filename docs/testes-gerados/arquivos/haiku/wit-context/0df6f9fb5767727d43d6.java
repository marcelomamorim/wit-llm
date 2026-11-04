/*
 * @test
 * @summary Verifies that the Lock views of StampedLock throw UnsupportedOperationException from newCondition()
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        StampedLock sl = new StampedLock();
        checkNewConditionThrows(sl.asWriteLock(), "asWriteLock");
        checkNewConditionThrows(sl.asReadLock(), "asReadLock");
    }

    static void checkNewConditionThrows(Lock lock, String source) {
        boolean thrown = false;
        try {
            lock.newCondition();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("newCondition() did not throw UnsupportedOperationException for " + source);
        }
    }
}
