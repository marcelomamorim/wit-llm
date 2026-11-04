/*
 * @test
 * @summary StampedLock.newCondition must throw UnsupportedOperationException and leave the lock usable
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        StampedLock lock = new StampedLock();

        expectUnsupported(lock);
        expectUnsupported(lock);

        long ws = lock.writeLock();
        lock.unlockWrite(ws);

        long st = lock.tryOptimisticRead();
        if (st == 0L || !lock.validate(st)) {
            throw new RuntimeException("optimistic read should validate after newCondition failures");
        }
    }

    private static void expectUnsupported(StampedLock lock) {
        try {
            lock.newCondition();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException from newCondition");
    }
}
