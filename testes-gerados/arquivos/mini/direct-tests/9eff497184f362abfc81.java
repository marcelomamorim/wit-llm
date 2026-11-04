/*
 * @test
 * @summary Test basic behavior of ReentrantLock.lock()
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {
    public static void main(String[] args) throws Exception {
        testLockBasic();
        testLockReentrancy();
    }

    static void testLockBasic() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            // After locking, current thread should hold the lock
            if (!lock.isHeldByCurrentThread()) {
                throw new RuntimeException("Lock should be held by current thread after lock()");
            }
            // Hold count should be 1
            if (lock.getHoldCount() != 1) {
                throw new RuntimeException("Hold count should be 1 after first lock()");
            }
        } finally {
            lock.unlock();
        }
        // After unlock, lock should not be held by current thread
        if (lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Lock should not be held by current thread after unlock()");
        }
    }

    static void testLockReentrancy() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            lock.lock();
            try {
                // Hold count should be 2 after reentrant lock
                if (lock.getHoldCount() != 2) {
                    throw new RuntimeException("Hold count should be 2 after reentrant lock()");
                }
                if (!lock.isHeldByCurrentThread()) {
                    throw new RuntimeException("Lock should be held by current thread after reentrant lock()");
                }
            } finally {
                lock.unlock();
            }
            // After one unlock, hold count should be 1
            if (lock.getHoldCount() != 1) {
                throw new RuntimeException("Hold count should be 1 after one unlock()");
            }
        } finally {
            lock.unlock();
        }
        // After all unlocks, lock should not be held
        if (lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Lock should not be held by current thread after all unlocks()");
        }
    }
}
