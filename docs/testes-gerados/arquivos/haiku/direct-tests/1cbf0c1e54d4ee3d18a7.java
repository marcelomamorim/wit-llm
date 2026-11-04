/*
 * @test
 * @summary ReentrantReadWriteLock.ReadLock.newCondition must throw UnsupportedOperationException
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        checkReadLockNewCondition(new ReentrantReadWriteLock());
        checkReadLockNewCondition(new ReentrantReadWriteLock(false));
        checkReadLockNewCondition(new ReentrantReadWriteLock(true));
    }

    static void checkReadLockNewCondition(ReentrantReadWriteLock lock) {
        boolean thrown = false;
        try {
            lock.readLock().newCondition();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("readLock().newCondition() did not throw UnsupportedOperationException");
        }
    }
}
