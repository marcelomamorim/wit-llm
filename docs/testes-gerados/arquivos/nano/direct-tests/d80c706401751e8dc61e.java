/* @test    @summary Test unlocking without prior lock acquisition in ReentrantReadWriteLock    @run main ReentrantReadWriteLockWitupTest */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testUnlockWithoutLock();
    }

    public static void testUnlockWithoutLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            lock.unlock();
            System.out.println("Test failed: No exception thrown when unlocking without lock.");
        } catch (IllegalMonitorStateException e) {
            System.out.println("Test passed: Caught expected IllegalMonitorStateException.");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }
}
