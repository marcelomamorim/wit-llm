/*
 * @test
 * @summary StampedLock read and write lock views must reject newCondition with UnsupportedOperationException
 * @run main StampedLockWitupTest
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {
    public static void main(String[] args) {
        StampedLock sl = new StampedLock();
        checkNewConditionThrows(sl.asReadLock(), "read");
        checkNewConditionThrows(sl.asWriteLock(), "write");
    }

    static void checkNewConditionThrows(Lock lock, String mode) {
        try {
            lock.newCondition();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("newCondition() did not throw UnsupportedOperationException for " + mode + " view");
    }
}
