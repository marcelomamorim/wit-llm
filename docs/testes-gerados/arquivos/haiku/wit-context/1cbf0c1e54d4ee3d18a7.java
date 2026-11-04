/*
 * @test
 * @summary ReadLock.newCondition() must throw UnsupportedOperationException
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) {
        checkNewConditionThrows(new ReentrantReadWriteLock());
        checkNewConditionThrows(new ReentrantReadWriteLock(true));
    }

    private static void checkNewConditionThrows(ReentrantReadWriteLock lock) {
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        try {
            readLock.newCondition();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("ReadLock.newCondition() did not throw UnsupportedOperationException");
    }
}
